import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';

import LoginForm from './components/login/LoginForm';
import BookForm from './components/books/BookForm';
import UserForm from './components/users/UserForm';
import UserTable from './components/users/UserTable';
import ReviewForm from './components/reviews/ReviewForm';
import ReviewList from './components/reviews/ReviewList';
import BookList from './components/books/BookList';
import MainPage from './components/MainPage';
import LoansNavigation from './components/loans/LoansNavigation';
import LoanBorrowForm from './components/loans/LoanBorrowForm';
import MyLoansList from './components/loans/MyLoansList';
import AllLoansList from './components/loans/AllLoansList';

import './components/css_files/navbar.css';
import ApiProvider from './ApiProvider';

import { useState } from 'react';

function App() {

  const [token, setToken] = useState<string | null>(localStorage.getItem("token"));
  const [role, setRole] = useState<string | null>(localStorage.getItem("role"));

  const refreshAuthState = () => {
    setToken(localStorage.getItem("token"));
    setRole(localStorage.getItem("role"));
  };

  const isLoggedIn = token;
  const isLibrarian = role === "LIBRARIAN";

  return (

    <BrowserRouter>
      <ApiProvider>
        <nav className="navbar">
          <Link to="/">Home</Link>
          <Link to="/books">Books</Link>
          <Link to="/reviews">Reviews</Link>

          {isLoggedIn && (
            <>
              <LoansNavigation />

              {isLibrarian && (
                <Link to="/users">Users</Link>
              )}
            </>
          )}


          <Link to="/login">{isLoggedIn ? "Account" : "Login"} </Link>

        </nav>

        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<LoginForm onAuthChange={refreshAuthState} />} />
          <Route path="/books" element={<BookList />} />
          <Route path="/books/add" element={<BookForm />} />
          <Route path="/reviews" element={<ReviewList />} />
          <Route path="/reviews/add" element={<ReviewForm />} />
          <Route path="/users" element={<UserTable />} />
          <Route path="/users/add" element={<UserForm />} />
          <Route path="/loans/borrow" element={<LoanBorrowForm />} />
          <Route path="/loans/my" element={<MyLoansList />} />
          <Route path="/loans/all" element={<AllLoansList />} />
        </Routes>

      </ApiProvider>
    </BrowserRouter>
  );

}

export default App;
