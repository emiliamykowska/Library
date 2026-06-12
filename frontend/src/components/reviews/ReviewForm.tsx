import { Button, TextField } from "@mui/material";
import "../css_files/Form.css";
import AddIcon from "@mui/icons-material/Add";
import { Formik } from "formik";
import { useCallback, useMemo, useState, useEffect } from "react";
import * as yup from "yup";
import type { ReviewFormValues } from "../reviews/ReviewFormValues";
import { LibraryClient } from "../../api/library-client";
import { useNavigate, useParams } from "react-router-dom";

function ReviewForm() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const navigate = useNavigate();

    const client = useMemo(() => 
        new LibraryClient(), []);

    const { reviewId } = useParams<{ reviewId: string }>();

    const isEditing = !!reviewId

    const [formValues, setFormValues] = useState<ReviewFormValues | null>(null);

    useEffect(() => {
        if (reviewId) {                        
            client.reviews.getReview(Number(reviewId)) 
                .then((response) => {
                    if (response.success && response.data) {
                        setFormValues(response.data);
                    }
                })
                .catch((error) => console.error("Error fetching book data:", error));
        } else {
            setFormValues({ bookId: 1, rating: 10, comment: '', userId: 1, });
        }
    }, [reviewId, client]);

    const onSubmit = useCallback(
            async (values: ReviewFormValues) => {
                if (isEditing && reviewId) {
                    const result = await client.reviews.updateReview(
                        Number(reviewId),
                        values
                    );
    
                    if (result.success) {
                        console.log("Review updated");
                        navigate('/reviews')
                    }
                } else {
                    const result = await client.reviews.addReview(values);
    
                    if (result.success) {
                        console.log("Review added");
                        navigate('/reviews')
                    }
                }
            },
            [client, isEditing, reviewId]
        );

    const validationSchema = useMemo(() =>
        yup.object({
            bookId: yup.number()
                .required("Book ID is required").positive("Book ID must be positive"),
            rating: yup.number()
                .required("Rating is required")
                .min(0, "Min rating is 0")
                .max(10, "Max rating is 10"),
            comment: yup.string().required("Comment is required"),
            userId: yup.number().when([], {
                is: () => isLibrarian,
                then: (schema) => schema.required("User ID is required").positive("User ID must be positive"),
                otherwise: (schema) => schema.notRequired()
            })
        }),
        [isLibrarian]);

    if (!formValues) {
        return <div>Loading review details</div>;
    }

    return (
        <div>
            <Formik<ReviewFormValues>
                initialValues={formValues}
                enableReinitialize
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
                                htmlInput: { min: 1 }
                            }}
                            value={formik.values.bookId}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.bookId && !!formik.errors.bookId}
                            helperText={formik.touched.bookId && formik.errors.bookId}
                        />

                        {isLibrarian && (                            
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
                        )}

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
                            value={formik.values.rating}
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
                            value={formik.values.comment}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.comment && !!formik.errors.comment}
                            helperText={formik.touched.comment && formik.errors.comment}
                        />
                        <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            type="submit"
                            sx={{backgroundColor: "#8b6f4f"}}
                            disabled={!formik.dirty || !formik.isValid}>
                            <span>{isEditing? "Edit Review" : "Add Review"} </span>
                        </Button>
                    </form>
                )}
            </Formik>
        </div>
    );
}

export default ReviewForm;