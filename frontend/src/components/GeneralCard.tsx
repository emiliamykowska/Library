import { Card, CardContent } from "@mui/material";
import type { ReactNode } from "react";

interface GeneralCardProps {
    children: ReactNode;
}

function GeneralCard({ children }: GeneralCardProps) {
    return (
        <Card sx={{
            width: '100%',
            minHeight: '250px',
            borderRadius: '30px',
            display: 'flex',
            flexDirection: 'column',
            boxShadow: "0px 8px 24px rgba(0,0,0,0.12)",
        }}>
            <CardContent sx={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                textAlign: 'center',
                flexGrow: 1,
                padding: '20px !important',
            }}>
                {children}
            </CardContent>
        </Card>
    );
}

export default GeneralCard;