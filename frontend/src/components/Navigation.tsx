import { Link } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import './css_files/navbar.css';

interface NavigationProps {
  token: string | null;
  role: string | null;
}

function Navigation({ token, role }: NavigationProps) {
  const isLoggedIn = !!token;
  const isLibrarian = role === "LIBRARIAN";

  return (
    <Navbar expand="lg" className="navbar">
      <Container>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/">Home</Nav.Link>
            
            {isLibrarian ? (
                <NavDropdown title="Books" id="books-nav-dropdown">
                <NavDropdown.Item as={Link} to="/books">All Books</NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/books/add">Add Book</NavDropdown.Item>
              </NavDropdown>
            ) : (
                <Nav.Link as={Link} to="/books">Books</Nav.Link>
            )}
            
            {isLoggedIn ? (
            <NavDropdown title="Reviews" id="reviews-nav-dropdown">
                <NavDropdown.Item as={Link} to="/reviews">All Reviews</NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/reviews/add">Add Review</NavDropdown.Item>  
            </NavDropdown>
            ) : (
            <Nav.Link as={Link} to="/reviews">Reviews</Nav.Link>
            )}

            {isLoggedIn && (
              <NavDropdown title="Loans" id="loans-nav-dropdown">
                {isLibrarian ? (
                  <NavDropdown.Item as={Link} to="/loans/all">All Loans</NavDropdown.Item>
                ) : (
                  <NavDropdown.Item as={Link} to="/loans/my">My Loans</NavDropdown.Item>
                )}
                <NavDropdown.Item as={Link} to="/loans/borrow">Borrow Book</NavDropdown.Item>
                {isLibrarian &&
                  <NavDropdown.Item as={Link} to="/loans/return/{id}">Return Book</NavDropdown.Item>
                }
                
              </NavDropdown>
            )}

            {isLoggedIn && isLibrarian && (
                <NavDropdown title="Users" id="users-nav-dropdown">
                <NavDropdown.Item as={Link} to="/users">All Users</NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/users/add">Add User</NavDropdown.Item>  
            </NavDropdown>
            )}
          </Nav>
          
          <Nav>
            <Nav.Link as={Link} to="/login" className="fw-bold">
              {isLoggedIn ? "Account" : "Login"}
            </Nav.Link>
          </Nav>

        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Navigation;