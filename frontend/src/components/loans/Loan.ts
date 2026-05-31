export interface Loan {
    loanId: number;
    bookId: number;
    bookTitle: string;
    userId: number;
    username: string;
    loanDate: string | Date;
    dueDate: string | Date;
    returnDate: string | Date;
}