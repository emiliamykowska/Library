import type { Loan } from "../loans/Loan";
import GeneralCard from "../GeneralCard";
import { Typography, IconButton, Box } from "@mui/material";
import AssignmentReturn from "@mui/icons-material/AssignmentReturn";
import { useApi } from "../../ApiProvider";
import { useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";

interface LoanCardProps {
    loan: Loan;
    isLibrarian?: boolean;
    onDelete?: (loanId: number) => void;
}


function LoanCard(
    { loan, isLibrarian, onDelete
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
            {isLibrarian && 
                (<Typography variant="h6">
                    Loan Id: {loan.loanId}
                </Typography>  )  
            }    
            
 
            <Typography variant={isLibrarian ? "body1" : "h6"}>
                {loan.bookTitle}
            </Typography>

            {isLibrarian && (
                <Typography variant="h6" >
                    Book Id: {loan.bookId}
                </Typography>
            )}
            

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

            {isLibrarian && loan.returnDate !== null &&(                        
                    <IconButton 
                        title="Delete" 
                        color="error" 
                        onClick={() => onDelete?.(loan.loanId)}>
                        <DeleteIcon />
                    </IconButton>
                    
                    
                )}

        </GeneralCard >
    )
}

export default LoanCard;