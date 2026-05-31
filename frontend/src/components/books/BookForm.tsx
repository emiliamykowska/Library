import { Button, TextField } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo } from "react";
import * as yup from "yup";
import type { BookFormValues } from "../books/BookFormValues";

function BookForm() {
    const onSubmit = useCallback(
        (values: BookFormValues,
            formik: any) => {
            const formattedValues = {
                ...values, //take all the values and copy here
                year: Number(values.year), //overwrite to convert string to number
                availableCopies: Number(values.availableCopies),
            };

            console.log(formattedValues);
        }, []
    );

    const validationSchema = useMemo(() => yup.object().shape({
        title: yup.string().required("Title is required!"),
        author: yup.string().required("Author is required!"),
        availableCopies: yup.number().required("Number of available copies is required").min(0, "Number of available copies cannot be negative"),
        isbn: yup.string()
            .transform((value) => value.replace(/-/g, "")) // to delete dashes before testing
            .required("ISBN number is required")
            .matches(/^\d{13}$/, "ISBN can contain only dashes and exactly 13 digits!"),
        publisher: yup.string().required("Publisher is required"),
        year: yup.number().required("Year is required").positive("Year cannot be negative")
    }), []);

    return (
        <div>
            <Formik<BookFormValues> initialValues={{ title: '', author: '', availableCopies: 0, isbn: '', publisher: '', year: 0 }}
                onSubmit={onSubmit}
                validationSchema={validationSchema}
                validateOnChange
                validateOnBlur>
                {(formik: any) => (
                    <form className='form' id={"addBookForm"} onSubmit={formik.handleSubmit}>
                        <TextField
                            id="title"
                            name="title"
                            label="Title:"
                            variant="standard"
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.title && !!formik.errors.title}
                            helperText={formik.touched.title && formik.errors.title} />
                        <TextField
                            id="author"
                            name="author"
                            label="Author:"
                            variant="standard"
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.author && !!formik.errors.author}
                            helperText={formik.touched.author && formik.errors.author}
                        />
                        <TextField
                            id="availableCopies"
                            name="availableCopies"
                            label="Available Copies:"
                            variant="standard"
                            type="number"
                            slotProps={{
                                htmlInput: { min: 0 }
                            }}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.availableCopies && !!formik.errors.availableCopies}
                            helperText={formik.touched.availableCopies && formik.errors.availableCopies}
                        />
                        <TextField
                            id="isbn"
                            name="isbn"
                            label="ISBN:"
                            variant="standard"
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.isbn && !!formik.errors.isbn}
                            helperText={formik.touched.isbn && formik.errors.isbn}
                        />
                        <TextField
                            id="publisher"
                            name="publisher"
                            label="Publisher:"
                            variant="standard"
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.publisher && !!formik.errors.publisher}
                            helperText={formik.touched.publisher && formik.errors.publisher}
                        />
                        <TextField
                            id="year"
                            name="year"
                            label="Year:"
                            variant="standard"
                            type="number"
                            slotProps={{
                                htmlInput: { min: 0 }
                            }}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.year && !!formik.errors.year}
                            helperText={formik.touched.year && formik.errors.year}
                        />
                        <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            type="submit"
                            disabled={!formik.dirty || !formik.isValid}>
                            Add Book
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );

}

export default BookForm;
