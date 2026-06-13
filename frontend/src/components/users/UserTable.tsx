import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  IconButton,
  Box,
} from "@mui/material";

import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

import "../css_files/List.css";

import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { User } from "./User";

import { useNavigate } from "react-router-dom";

function UserTable() {
  const navigate = useNavigate()
  const apiClient = useApi();  

  const [users, setUsers] = useState<User[]>([]);

  const onDelete = async (userId: number) => {
        const result = await apiClient.users.deleteUser(userId);

        if (result.success) {
            setUsers(users =>
                users.filter(user => user.userId !== userId)
            );
        }
    };

  useEffect(() => {
    apiClient.users.getUsers().then((response) => {
      if (response.success && response.data) {
        setUsers(response.data);
      }
    });
  }, [apiClient]);

  return (
    <Box className="list-form">
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Username</TableCell>
            <TableCell>User ID</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Name</TableCell>
            <TableCell>Role</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {users.map((user) => (
            <TableRow key={user.userId}>
              <TableCell>{user.username}</TableCell>
              <TableCell>{user.userId}</TableCell>
              <TableCell>{user.email}</TableCell>
              <TableCell>{user.name}</TableCell>
              <TableCell>{user.role}</TableCell>
              <TableCell>
                <IconButton onClick={() => navigate((`/users/edit/${user.userId}`))}>
                  <EditIcon />
                </IconButton>

                {user.userId !== 9999 &&
                  <IconButton color="error" onClick={() => onDelete?.(user.userId)}>
                    <DeleteIcon />
                  </IconButton>}
                
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Box>
  );
}

export default UserTable;
