import type { Loan } from "../loans/Loan";
import GeneralCard from "../GeneralCard";
import { Typography, IconButton, Box } from "@mui/material";
import AssignmentReturn from "@mui/icons-material/AssignmentReturn";
import { useApi } from "../../ApiProvider";
import { useState } from "react";

interface LoanCardProps {
    loan: Loan;
    isLibrarian?: boolean;
}


function LoanCard(
    { loan, isLibrarian
    }: LoanCardProps
) {
    const apiClient = useApi();

    const [returnDate, setReturnDate] = useState<string | Date | null>(loan.returnDate);

    const onReturn = async () => {
        const result = await apiClient.loans.returnBook(
            loan.loanId,
            {}
        );

        if (result.success) {
            console.log("Book returned");
            setReturnDate(new Date().toLocaleDateString());
        }
    };

    return (
        <GeneralCard>          
            <Typography variant="h6">
                Loan Id: {loan.loanId}
            </Typography>  
            <Typography>
                {loan.bookTitle}
            </Typography>
            <Typography variant="h6" >
                Book Id: {loan.bookId}
            </Typography>

            {isLibrarian && (<Box>
            <Typography>
                {loan.username}
            </Typography>
                <Typography variant="h6">
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
                Return Date: {returnDate !== null ? String(returnDate) : (
                <>        
                    <span style={{ color: "#c62828", fontWeight: 600 }}>
                        The book was not returned yet
                    </span>
                    
                    <IconButton 
                        title="Return with today's date" 
                        onClick={onReturn}>
                        <AssignmentReturn/>
                    </IconButton>
                </>
    )}
            </Typography>
        </GeneralCard >
    )
}

export default LoanCard;