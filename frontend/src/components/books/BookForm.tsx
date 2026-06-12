import { Button, TextField } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo, useState, useEffect } from "react";
import * as yup from "yup";
import type { BookFormValues } from "../books/BookFormValues";
import { LibraryClient } from "../../api/library-client";
import { useNavigate, useParams } from "react-router-dom";


function BookForm() {
    const navigate = useNavigate();

    const client = useMemo(() => 
        new LibraryClient(), []);

    const { bookId } = useParams<{ bookId: string }>();

    const isEditing = !!bookId

    const [formValues, setFormValues] = useState<BookFormValues | null>(null);

    useEffect(() => {
        if (bookId) {                        
            client.books.getBook(Number(bookId)) 
                .then((response) => {
                    if (response.success && response.data) {
                        setFormValues(response.data);
                    }
                })
                .catch((error) => console.error("Error fetching book data:", error));
        } else {
            setFormValues({ title: '', author: '', availableCopies: 0, isbn: '', publisher: '', year: 0 });
        }
    }, [bookId, client]);
  
    const onSubmit = useCallback(
        async (values: BookFormValues) => {
            if (isEditing && bookId) {
                const result = await client.books.updateBook(
                    Number(bookId),
                    values
                );

                if (result.success) {
                    console.log("Book updated");
                    navigate('/books')
                }
            } else {
                const result = await client.books.addBook(values);

                if (result.success) {
                    console.log("Book added");
                    navigate('/books')
                }
            }
        },
        [client, isEditing, bookId]
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

    if (!formValues) {
        return <div>Loading book details</div>;
    }

    return (
        <div>
            <Formik<BookFormValues> initialValues={formValues}
                enableReinitialize
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
                            value={formik.values.title}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.title && !!formik.errors.title}
                            helperText={formik.touched.title && formik.errors.title} />
                        <TextField
                            id="author"
                            name="author"
                            label="Author:"
                            variant="standard"
                            value={formik.values.author}
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
                            value={formik.values.availableCopies}
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
                            value={formik.values.isbn}
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
                            value={formik.values.publisher}
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
                            value={formik.values.year}
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
                            <span>{isEditing? "Edit Book" : "Add Book"} </span>
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );

}

export default BookForm;
