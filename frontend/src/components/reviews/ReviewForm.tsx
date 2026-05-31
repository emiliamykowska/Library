import { Button, TextField } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo } from "react";
import * as yup from "yup";
import type { ReviewFormValues } from "../reviews/ReviewFormValues";

function ReviewForm() {
    const onSubmit = useCallback((values: ReviewFormValues,
        formik: any) => {
        const formattedValues = {
            ...values,
            bookId: Number(values.bookId),
            rating: Number(values.rating),
        };

        console.log(formattedValues);
    }, []);

    const validationSchema = useMemo(() =>
        yup.object({
            bookId: yup.number()
                .required("Book ID is required").positive("Book ID must be positive"),
            rating: yup.number()
                .required("Rating is required")
                .min(0, "Min rating is 0")
                .max(10, "Max rating is 10"),
            comment: yup.string().required("Comment is required"),
        }),
        []);

    return (
        <div>
            <Formik<ReviewFormValues>
                initialValues={{
                    bookId: 1, rating: 10, comment: '',
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
                            slotProps={{
                                htmlInput: { min: 0 }
                            }}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.bookId && !!formik.errors.bookId}
                            helperText={formik.touched.bookId && formik.errors.bookId}
                        />
                        <TextField
                            id="rating"
                            name="rating"
                            label="Rating (0-10):"
                            type="number"
                            variant="standard"
                            fullWidth
                            slotProps={{
                                htmlInput: { min: 0, max: 10 },

                            }}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.rating && !!formik.errors.rating}
                            helperText={formik.touched.rating && formik.errors.rating}
                        />
                        <TextField
                            id="comment"
                            name="comment"
                            label="Comment:"
                            multiline
                            variant="standard"
                            fullWidth
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.comment && !!formik.errors.comment}
                            helperText={formik.touched.comment && formik.errors.comment}
                        />
                        <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            type="submit"
                            disabled={!formik.dirty || !formik.isValid}
                        >
                            Add Review
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );
}

export default ReviewForm;