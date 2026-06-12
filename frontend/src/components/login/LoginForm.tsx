import { Button, TextField } from "@mui/material";
import "../css_files/Form.css";
import LoginIcon from "@mui/icons-material/Login";
import LogoutIcon from "@mui/icons-material/Logout";
import { Formik } from "formik";
import { useCallback, useMemo, useState } from "react";
import * as yup from "yup";
import { useNavigate } from "react-router";
import { useApi } from "../../ApiProvider";

interface LoginFormProps {
    onAuthChange: () => void;
}


function LoginForm({ onAuthChange }: LoginFormProps) {
    const apiClient = useApi();
    const navigate = useNavigate();

    const [localToken, setLocalToken] = useState<string | null>(localStorage.getItem("token"));
    const isLoggedIn = localToken;


    const onSubmit = useCallback(
        (values: { username: string, password: string }, formik: any) => {
            apiClient.login(values).then((response) => {
                if (response.success) {
                    onAuthChange();
                    setLocalToken(localStorage.getItem("token"));
                    navigate('/')
                } else {
                    formik.setFieldError('username', 'Invalid username or password');
                }
            })
        }, [apiClient, navigate, onAuthChange]); //react hook

    const onLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("role");

        onAuthChange();
        setLocalToken(null);

        navigate('/login')
    }

    const validationSchema = useMemo(() => yup.object().shape({
        username: yup.string().required("Username is required!"),
        password: yup.string().required("Password is required!").min(5, "Password is too short!"),
    }), []);


    if (isLoggedIn) {
        return (
            <div className='form'>
                <h2>Your account</h2>
                <p>
                    You are currently logged in.
                </p>

                <Button
                    variant="contained"
                    color="error"
                    startIcon={<LogoutIcon />}
                    onClick={onLogout}
                >
                    Logout
                </Button>
            </div>
        );
    }

    return (
        <div>
            <Formik initialValues={{ username: '', password: '' }}
                onSubmit={onSubmit}
                validationSchema={validationSchema}
                validateOnChange
                validateOnBlur>
                {(formik: any) => (
                    <form className='form' id={"signForm"} onSubmit={formik.handleSubmit}>
                        <TextField
                            id="username"
                            name="username"
                            label="Username:"
                            variant="standard"
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.username && !!formik.errors.username}
                            helperText={formik.touched.username && formik.errors.username} />
                        <TextField
                            id="password"
                            name="password"
                            label="Password:"
                            variant="standard"
                            type="password"
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.password && !!formik.errors.password}
                            helperText={formik.touched.password && formik.errors.password}
                        />
                        <Button
                            variant="contained"
                            startIcon={<LoginIcon />}
                            type="submit"
                            sx={{backgroundColor: "#8b6f4f"}}
                            disabled={!formik.dirty || !formik.isValid}>                                
                            Login
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );

}

export default LoginForm;