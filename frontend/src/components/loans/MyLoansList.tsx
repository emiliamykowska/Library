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
    },
        [apiClient])


    return (
        <div className="list-form">
            <h1>List of Loans</h1>

            {
                loans.map((loan) => (
                    <LoanCard
                        key={loan.loanId}
                        loan={loan}
                        isLibrarian={false}
                    />
                ))
            }
        </div >
    );
}

export default MyLoansList;
