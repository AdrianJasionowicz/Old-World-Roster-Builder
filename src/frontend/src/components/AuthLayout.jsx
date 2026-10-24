import "../styles/auth.css";

function AuthLayout({ children }) {
    return (
        <div className="auth-container">
            <div className="auth-card">
                <h1 className="logo">
                    <i className="bi bi-shield-shaded me-2" />
                    Old World Roster Builder
                </h1>
                <p className="subtitle">Warhammer: The Old World</p>
                {children}
            </div>
        </div>
    );
}

export default AuthLayout;
