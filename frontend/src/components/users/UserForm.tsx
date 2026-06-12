import { Button, TextField } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo, useEffect, useState } from "react";
import { MenuItem } from "@mui/material";
import * as yup from "yup";
import type { UserFormValues } from "../users/UserFormValues";
import { LibraryClient } from "../../api/library-client";
import { useNavigate, useParams } from "react-router-dom";

function UserForm() {
    const navigate = useNavigate();
    
    const client = useMemo(() => 
        new LibraryClient(), []);

    const { userId } = useParams<{ userId: string }>();

    const isEditing = !!userId

    const [formValues, setFormValues] = useState<UserFormValues | null>(null);

    useEffect(() => {
        if (userId) {                        
            client.users.getUser(Number(userId)) 
                .then((response) => {
                    if (response.success && response.data) {
                        setFormValues(response.data);
                    }
                })
                .catch((error) => console.error("Error fetching book data:", error));
        } else {
            setFormValues({ email: '', name: '', username: '', password: '', role: 'READER' });
        }
    }, [userId, client]);

    const onSubmit = useCallback(
            async (values: UserFormValues) => {
                if (isEditing && userId) {
                    const result = await client.users.updateUser(
                        Number(userId),
                        values
                    );
    
                    if (result.success) {
                        console.log("User updated");
                        navigate('/users')
                    }
                } else {
                    const result = await client.users.addUser(values);
    
                    if (result.success) {
                        console.log("User added");
                        navigate('/users')
                    }
                }
            },
            [client, isEditing, userId]
        );

    const validationSchema = useMemo(() => yup.object().shape({
        email: yup.string().required("Email is required!").email("Invalid email!"),
        name: yup.string().required("Name is required!"),
        username: yup.string().required("Username is required"),
        password: yup.string().required("Password is required").min(6, "Password has to be at least 6 characters long"),
        role: yup.string().required("Role is required")
    }), []);

    if (!formValues) {
        return <div>Loading user details</div>;
    }

    return (
        <div>
            <Formik<UserFormValues> 
                initialValues={formValues}
                enableReinitialize
                onSubmit={onSubmit}
                validationSchema={validationSchema}
                validateOnChange
                validateOnBlur>
                {(formik: any) => (
                    <form className='form' id={"addUserForm"} onSubmit={formik.handleSubmit}>
                        <TextField
                            id="email"
                            name="email"
                            label="Email:"
                            variant="standard"
                            fullWidth
                            value={formik.values.email}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.email && !!formik.errors.email}
                            helperText={formik.touched.email && formik.errors.email} />
                        <TextField
                            id="name"
                            name="name"
                            label="Name:"
                            variant="standard"
                            fullWidth
                            value={formik.values.name}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.name && !!formik.errors.name}
                            helperText={formik.touched.name && formik.errors.name}
                        />
                        <TextField
                            id="username"
                            name="username"
                            label="Username:"
                            variant="standard"
                            fullWidth
                            value={formik.values.username}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.username && !!formik.errors.username}
                            helperText={formik.touched.username && formik.errors.username}
                        />
                        <TextField
                            id="password"
                            name="password"
                            label="Password:"
                            variant="standard"
                            type="password"
                            fullWidth
                            value={formik.values.password}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.password && !!formik.errors.password}
                            helperText={formik.touched.password && formik.errors.password}
                        />
                        <TextField
                            id="role"
                            name="role"
                            select // To change it to dropdown
                            fullWidth
                            label="Role:"
                            variant="standard"
                            value={formik.values.role}
                            slotProps={{
                                inputLabel: {
                                    sx: {
                                        fontSize: '1rem'
                                    }
                                },
                                select: {
                                    sx: {
                                        textAlign: 'left',
                                    }
                                }
                            }}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.role && !!formik.errors.role}
                            helperText={formik.touched.role && formik.errors.role}
                        >
                            <MenuItem value="READER">Reader</MenuItem>
                            <MenuItem value="LIBRARIAN">Librarian</MenuItem>
                        </TextField>
                        <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            type="submit"
                            disabled={!formik.dirty || !formik.isValid}>
                            <span>{isEditing? "Edit User" : "Add User"} </span>
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );

}

export default UserForm;
