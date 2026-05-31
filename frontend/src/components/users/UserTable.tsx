import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  IconButton,
  Button,
  Box,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

import { Link } from "react-router-dom";
import "../css_files/List.css";

import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { User } from "./User";

function UserTable() {
  const apiClient = useApi();

  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    apiClient.users.getUsers().then((response) => {
      if (response.success && response.data) {
        setUsers(response.data);
      }
    });
  }, [apiClient]);

  return (
    <Box className="list-form">
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
          width: "100%",
          marginBottom: 4,
          paddingX: 2,
        }}
      >
        <h2>Users</h2>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          component={Link}
          to="/users/add"
        >
          Add User
        </Button>
      </Box>

      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Username</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Role</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {users.map((user) => (
            <TableRow key={user.userId}>
              <TableCell>{user.username}</TableCell>
              <TableCell>{user.email}</TableCell>
              <TableCell>{user.role}</TableCell>
              <TableCell>
                <IconButton>
                  <EditIcon />
                </IconButton>
                <IconButton color="error">
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Box>
  );
}

export default UserTable;
