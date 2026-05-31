import { useState } from "react";
import type { MouseEvent } from "react";
import { Button, Menu, MenuItem } from "@mui/material";
import { Link } from "react-router-dom";

function LoansNavigation() {
    const isLibrarian = localStorage.getItem("role") === "LIBRARIAN";
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

    const handleOpen = (event: MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    return (
        <>
            <Button 
                onClick={handleOpen}
                disableRipple // Removes the heavy Material-UI ripple effect on click
                sx={{ 
                    // Matches your .navbar a styles exactly
                    color: "#333",
                    fontWeight: 800,
                    fontSize: "inherit", // Inherits the font size from your navbar layout
                    textTransform: "none", // Prevents MUI from forcing UPPERCASE text
                    fontFamily: "inherit",
                    padding: 0, // Clears button padding so it aligns perfectly with your normal links
                    minWidth: "auto",
                    transition: "0.2s",
                    
                    // Matches your .navbar a:hover styles exactly
                    "&:hover": {
                        color: "#8b6f47",
                        backgroundColor: "transparent" // Prevents MUI from showing a gray box on hover
                    }
                }}
            >
                Loans
            </Button>
            
            <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleClose}
                // Adds a clean dropdown shadow and matches your background theme
                slotProps={{
                    paper: {
                        sx: {
                            backgroundColor: "#f0efe6", 
                            boxShadow: "0px 4px 12px rgba(0, 0, 0, 0.08)",
                            marginTop: "0.5rem"
                        }
                    }
                }}
            >
                <MenuItem 
                    component={Link} 
                    to="/loans/borrow" 
                    onClick={handleClose}
                    sx={{ color: "#333", fontWeight: 600, "&:hover": { color: "#8b6f47" } }}
                >
                    Borrow Book
                </MenuItem>

                {!isLibrarian ? (
                    <MenuItem 
                        component={Link} 
                        to="/loans/my" 
                        onClick={handleClose}
                        sx={{ color: "#333", fontWeight: 600, "&:hover": { color: "#8b6f47" } }}
                    >
                        My Loans
                    </MenuItem>
                ) : (
                    <MenuItem 
                        component={Link} 
                        to="/loans/all" 
                        onClick={handleClose}
                        sx={{ color: "#333", fontWeight: 600, "&:hover": { color: "#8b6f47" } }}
                    >
                        All Active Loans
                    </MenuItem>
                )}
            </Menu>
        </>
    );
}

export default LoansNavigation;