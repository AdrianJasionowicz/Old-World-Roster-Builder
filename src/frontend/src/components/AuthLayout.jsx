import "../styles/auth.css";

function AuthLayout({ children }) {

    return (

        <div className="auth-container">

            <div className="auth-card">

                <h1 className="logo">
                    ⚔ Army Builder
                </h1>

                <p className="subtitle">
                    Warhammer Fantasy Battles
                </p>

                {children}

            </div>

        </div>

    );

}

export default AuthLayout;