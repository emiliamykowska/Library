import { Button, TextField, Box } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo, useState } from "react";
import * as yup from "yup";
import type { LoanFormValues } from "./LoanFormValues";

function LoanForm() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const getTodayDateString = () => new Date().toISOString().split("T")[0]; // T marks the end of the date 
    const getFutureDateString = () => {
        const date = new Date();
        date.setDate(date.getDate() + 30);

        return date.toISOString().split("T")[0];
    };

    const onSubmit = useCallback((values: LoanFormValues, formik: any) => {
        const formattedValues = {
            bookId: Number(values.bookId),

            ...(isLibrarian && {
                userId: Number(values.userId),
                loanDate: values.loanDate,
                dueDate: values.dueDate,
            })
        };

        console.log(formattedValues);
    }, []);

    const validationSchema = useMemo(() =>
        yup.object({
            bookId: yup.number()
                .required("Book ID is required")
                .positive("Book ID must be positive"),
            userId: yup.number().when([], {
                is: () => isLibrarian,
                then: (schema) => schema.required("User ID is required").positive("User ID must be positive"),
                otherwise: (schema) => schema.notRequired()
            }),
            loanDate: yup.date().nullable(),
            dueDate: yup.date().nullable()
        }),
        []);

    return (
        <div>
            <Formik
                initialValues={{
                    bookId: 1,
                    userId: 1,
                    loanDate: getTodayDateString(),
                    dueDate: getFutureDateString(),
                }}
                onSubmit={onSubmit}
                validationSchema={validationSchema}
                validateOnChange
                validateOnBlur
            >
                {(formik) => (
                    <form className="form" onSubmit={formik.handleSubmit}>                        
                        <TextField
                            id="bookId"
                            name="bookId"
                            label="Book ID:"
                            type="number"
                            variant="standard"
                            fullWidth
                            slotProps={{ htmlInput: { min: 1 } }}
                            value={formik.values.bookId}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.bookId && !!formik.errors.bookId}
                            helperText={formik.touched.bookId && formik.errors.bookId}
                        />
                        {isLibrarian && (
                                <Box sx={{width: "100%", 
                                        display: "flex", 
                                        flexDirection: "column", 
                                        gap: "3rem"
                                    }}>
                                <TextField
                                    id="userId"
                                    name="userId"
                                    label="User ID:"
                                    type="number"
                                    variant="standard"
                                    fullWidth
                                    slotProps={{ htmlInput: { min: 1 } }}
                                    value={formik.values.userId}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    error={formik.touched.userId && !!formik.errors.userId}
                                    helperText={formik.touched.userId && formik.errors.userId}
                                />

                                <TextField
                                    id="loanDate"
                                    name="loanDate"
                                    label="Loan Date"
                                    type="date"
                                    variant="standard"
                                    fullWidth
                                    slotProps={{inputLabel: { shrink: true }}}
                                    value={formik.values.loanDate}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                />

                                <TextField
                                    id="dueDate"
                                    name="dueDate"
                                    label="Due Date"
                                    type="date"
                                    variant="standard"
                                    fullWidth
                                    slotProps={{inputLabel: { shrink: true }}}
                                    value={formik.values.dueDate}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                />
                            </Box>
                        )}

                        <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            type="submit"
                            disabled={!formik.dirty || !formik.isValid}
                            sx={{ mt: 3 }}
                        >
                            Borrow Book
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );
}

export default LoanForm;