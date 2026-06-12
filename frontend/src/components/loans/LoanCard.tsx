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
            <Typography variant="h5" sx={{ fontWeight: 600 }}>
                {loan.bookTitle}
            </Typography>
            <Typography variant="h5" >
                Book Id: {loan.bookId}
            </Typography>

            {isLibrarian && (<Box>
            <Typography variant="h5" sx={{ fontWeight: 600 }}>
                {loan.username}
            </Typography>
                <Typography variant="h5">
                User Id: {loan.userId}
            </Typography>
            </Box>
            )}

            <Typography>
                Loan Date: {String(loan.loanDate)}
            </Typography>
            <Typography>
                Due Date: {String(loan.dueDate)}
            </Typography>
            <Typography>
                Return Date: {loan.returnDate !== null ? String(loan.returnDate) : (
        <>        
            <span style={{ color: "#c62828", fontWeight: 600 }}>
                The book was not returned yet
            </span>
            <IconButton>
                <AssignmentReturn />
            </IconButton>
        </>
    )}
            </Typography>
        </GeneralCard >
    )
}

export default LoanCard;