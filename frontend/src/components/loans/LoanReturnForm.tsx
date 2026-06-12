import { Button, TextField } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo, useState } from "react";
import * as yup from "yup";
import type { LoanReturnValues } from "./LoanReturnValues";
import { LibraryClient } from "../../api/library-client";
import { useNavigate } from "react-router-dom";

function LoanForm() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const navigate = useNavigate()
    
    const client = useMemo(() => new LibraryClient(), []);

    const getTodayDateString = () => new Date().toISOString().split("T")[0]; 
    
    const onSubmit = useCallback(
            async (values: LoanReturnValues) => {
                    const result = await client.loans.returnBook(
                        values.loanId,
                        {   
                            returnDate: values.returnDate
                        }
                    );
    
                    if (result.success) {
                        console.log("Book returned");
                        navigate('/loans')
                    }
                },            
            [client]
        );

    const validationSchema = useMemo(() =>
        yup.object({
            loanId: yup.number()
                .required("Loan ID is required")
                .positive("Loan ID must be positive"),
            returnDate: yup.date().nullable()
        }),
        []);
   

    return (
        <div>
            <Formik
                initialValues={{
                    loanId: 1,
                    returnDate: getTodayDateString()
                }}
                onSubmit={onSubmit}
                validationSchema={validationSchema}
                validateOnChange
                validateOnBlur
            >
                {(formik) => (
                    <form className="form" onSubmit={formik.handleSubmit}>  
                        <TextField
                            id="loanId"
                            name="loanId"
                            label="Loan ID:"
                            type="number"
                            variant="standard"
                            fullWidth
                            slotProps={{ htmlInput: { min: 1 } }}
                            value={formik.values.loanId}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.loanId && !!formik.errors.loanId}
                            helperText={formik.touched.loanId && formik.errors.loanId}
                        />

                        {isLibrarian && (
                            <TextField
                                id="returnDate"
                                name="returnDate"
                                label="Return Date"
                                type="date"
                                variant="standard"
                                fullWidth
                                slotProps={{inputLabel: { shrink: true }}}
                                value={formik.values.returnDate}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                error={formik.touched.returnDate && !!formik.errors.returnDate}
                                    helperText={formik.touched.returnDate && formik.errors.returnDate}
                            />
                        )}

                        <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            type="submit"
                            sx={{backgroundColor: "#8b6f4f"}}
                            disabled={!formik.dirty || !formik.isValid}
                        >
                            Return Book
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );
}

export default LoanForm;