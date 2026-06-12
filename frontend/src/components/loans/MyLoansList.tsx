import LoanCard from "./LoanCard";
import "../css_files/List.css";
import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { Loan } from "./Loan";

function MyLoansList() {

    const apiClient = useApi();

    const [loans, setLoans] = useState<Loan[]>([]);
    

    useEffect(() => {
        apiClient.loans.getMyLoans()
            .then((response) => {
                if (response.success && response.data) {
                    setLoans(response.data)
                }
                
            }            
            )
            .catch((error) => console.error("Error fetching loans:", error))
    },
        [apiClient])


    return (
        <div className="list-form">
            <h1>List of Loans</h1>

            {loans.length > 0 ? (                
                loans.map((loan) => (
                    <LoanCard
                        key={loan.loanId}
                        loan={loan}
                        isLibrarian={false}
                    />
                ))            
            ) : (                   
                <p>
                    You haven't loaned any books yet
                </p>
            )}
            
        </div >
    );
}

export default MyLoansList;
