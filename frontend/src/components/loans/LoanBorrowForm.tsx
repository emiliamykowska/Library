import { Button, TextField, Box } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo, useState } from "react";
import * as yup from "yup";
import type { LoanFormValues } from "./LoanFormValues";
import { LibraryClient } from "../../api/library-client";
import type { Book } from "../books/Book";
import { Autocomplete } from "@mui/material";
import { useNavigate } from "react-router-dom";

function LoanForm() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const navigate = useNavigate()
    
    const client = useMemo(() => new LibraryClient(), []);
    const [books, setBooks] = useState<Book[]>([]);
    const searchBooks = async (title: string) => {
        const result = await client.books.searchBooks(title);

        if (result.success && result.data) {
            setBooks(result.data);
        } else {
            setBooks([]);
        }
    };

    const getTodayDateString = () => new Date().toISOString().split("T")[0]; // T marks the end of the date 
    const getFutureDateString = () => {
        const date = new Date();
        date.setDate(date.getDate() + 30);

        return date.toISOString().split("T")[0];
    };

    const onSubmit = useCallback(
            async (values: LoanFormValues) => {
                    const result = isLibrarian 
                        ? await client.loans.borrowForUser(values.userId, values)
                        : await client.loans.borrowBook(values);
    
                    if (result.success) {
                        console.log("Loan added");
                        if (isLibrarian){
                            navigate('/loans/all')
                        }
                        else{
                             navigate('/loans/my')
                        }
                        
                    }
                },            
            [client]
        );

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
            dueDate: yup.date().nullable().min(yup.ref('loanDate'), "Due date cannot be before the loan date")
        }),
        [isLibrarian]);
   

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
                        {/* <TextField
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
                        /> */}

                        <Box
                            sx={{
                                width: "100%",
                            }}
                        >
                            <Autocomplete
                                fullWidth
                                options={books}
                                getOptionLabel={(option) => option.title}
                                onInputChange={(_, value) => {
                                    searchBooks(value);
                                }}
                                onChange={(_, selectedBook) => {
                                    formik.setFieldValue(
                                        "bookId",
                                        selectedBook?.bookId ?? 0
                                    );
                                }}
                                renderInput={(params) => (
                                    <TextField
                                        {...params}
                                        label="Book title"
                                        variant="standard"
                                        fullWidth
                                    />
                                )}
                            />
                        </Box>

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
                                    error={formik.touched.loanDate && !!formik.errors.loanDate}
                                    helperText={formik.touched.loanDate && formik.errors.loanDate}
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
                                    error={formik.touched.dueDate && !!formik.errors.dueDate}
                                    helperText={formik.touched.dueDate && formik.errors.dueDate}
                                />
                            </Box>
                        )}

                        <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            type="submit"
                            sx={{backgroundColor: "#8b6f4f"}}
                            disabled={!formik.dirty || !formik.isValid}
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