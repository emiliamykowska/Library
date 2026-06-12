import LoanCard from "./LoanCard";
import "../css_files/List.css";
import { useApi } from "../../ApiProvider";
import { useEffect, useState, useMemo } from "react";
import type { Loan } from "./Loan";
import { Box, TextField } from "@mui/material";

function AllLoansList() {

    const apiClient = useApi();

    const [loans, setLoans] = useState<Loan[]>([]);

    const [searchQuery, setSearchQuery] = useState<string>("");

    useEffect(() => {
        apiClient.loans.getLoans()
            .then((response) => {
                if (response.success && response.data) {
                    setLoans(response.data)
                }
            }
            )
    },
        [apiClient])

    const filteredLoans = useMemo(() => {
        if (!searchQuery.trim()) return loans;
        
        return loans.filter((loan) =>            
            loan.bookTitle?.toLowerCase().includes(searchQuery.toLowerCase())
        );
    }, [loans, searchQuery]);


    return (
        <Box sx={{ width: "100%", p: 2 }}>
            <TextField sx={{borderColor: "#8b6f47", width: "800px"}}
                        placeholder="Search loans by book title"
                        variant="outlined"                
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
            <div className="list-form">
                {
                    filteredLoans.map((loan) => (
                        <LoanCard
                            key={loan.loanId}
                            loan={loan}
                            isLibrarian={true}
                        />
                    ))
                }
            </div >

        </Box>
        
    );
}

export default AllLoansList;
