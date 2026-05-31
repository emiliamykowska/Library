import type { Loan } from "../loans/Loan";
import GeneralCard from "../GeneralCard";
import { Typography, IconButton, Box } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import AssignmentReturn from "@mui/icons-material/AssignmentReturn";

interface LoanCardProps {
    loan: Loan;
    isLibrarian?: boolean;
}


function LoanCard(
    { loan, isLibrarian
    }: LoanCardProps
) {
    return (
        <GeneralCard>
            <Typography variant="h5">
                Book Id: {loan.bookId}
            </Typography>
            <Typography variant="h5">
                Book Title: {loan.bookTitle}
            </Typography>

            {isLibrarian && (<Box>
                <Typography variant="h5">
                User Id: {loan.userId}
            </Typography>
            <Typography variant="h5">
                Username: {loan.username}
            </Typography></Box>
            )}

            <Typography variant="h6">
                Loan Date: {String(loan.loanDate)}
            </Typography>
            <Typography>
                Due Date: {String(loan.dueDate)}
            </Typography>
            <Typography>
                Return Date: {loan.returnDate !== null ? String(loan.returnDate) : (
        <>
            The book was not returned yet
            <IconButton>
                <AssignmentReturn />
            </IconButton>
        </>
    )}
            </Typography>

            {isLibrarian && (
                <Box>
                    <IconButton>
                        <EditIcon />
                    </IconButton>
                    <IconButton color="error">
                        <DeleteIcon />
                    </IconButton>
                </Box>
            )}
        </GeneralCard >
    )
}

export default LoanCard;