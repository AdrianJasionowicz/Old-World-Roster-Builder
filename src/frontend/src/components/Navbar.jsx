import { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";

import { exportArmyPdf } from "../api/armyApi";
import { logout, me } from "../api/authApi";

function Navbar({ army }) {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [exporting, setExporting] = useState(false);

    useEffect(() => {
        me().then(response => setUser(response.data)).catch(() => {});
    }, []);

    async function handleLogout() {
        try {
            await logout();
        } finally {
            navigate("/login");
        }
    }

    async function handleExportPdf() {
        setExporting(true);
        try {
            const response = await exportArmyPdf(army.id);
            const url = URL.createObjectURL(new Blob([response.data], { type: "application/pdf" }));
            window.open(url, "_blank");
        } catch {
            alert("Could not generate the PDF.");
        } finally {
            setExporting(false);
        }
    }

    const linkClass = ({ isActive }) => `btn btn-sm ${isActive ? "btn-light" : "btn-outline-light"}`;

    return (
        <nav className="navbar navbar-dark bg-dark px-3">
            <div className="d-flex align-items-center gap-3">
                <NavLink to="/rosters" className="navbar-brand fw-bold mb-0">
                    <i className="bi bi-shield-shaded me-2" />
                    Old World Roster Builder
                </NavLink>

                {army && (
                    <span className="text-light">
                        <strong>{army.name}</strong>
                        <small className="text-secondary ms-2">{army.pointsLimit} pts</small>
                    </span>
                )}
            </div>

            <div className="d-flex align-items-center gap-2">
                {army && (
                    <button className="btn btn-sm btn-warning" onClick={handleExportPdf} disabled={exporting}>
                        <i className="bi bi-file-earmark-pdf me-1" />
                        {exporting ? "Generating..." : "Export PDF"}
                    </button>
                )}

                <NavLink to="/rosters" end className={linkClass}>Rosters</NavLink>
                {user?.role === "ROLE_ADMIN" && <NavLink to="/admin" className={linkClass}>Admin</NavLink>}
                <NavLink to="/account" className={linkClass}>
                    <i className="bi bi-person me-1" />
                    {user?.username ?? "Account"}
                </NavLink>
                <button className="btn btn-sm btn-outline-danger" onClick={handleLogout}>Logout</button>
            </div>
        </nav>
    );
}

export default Navbar;
