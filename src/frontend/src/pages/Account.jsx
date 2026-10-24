import { useCallback, useEffect, useState } from "react";

import Navbar from "../components/Navbar";
import ChangeUsernameModal from "../components/ChangeUsernameModal";
import ChangeEmailModal from "../components/ChangeEmailModal";
import ChangePasswordModal from "../components/ChangePasswordModal";
import DeleteAccountModal from "../components/DeleteAccountModal";

import { getAccount } from "../api/accountApi";

import "../styles/account.css";

const ROLE_LABELS = { ROLE_USER: "User", ROLE_ADMIN: "Administrator" };

function Account() {
    const [account, setAccount] = useState(null);
    const [openModal, setOpenModal] = useState(null);

    const loadAccount = useCallback(() => {
        getAccount().then(response => setAccount(response.data)).catch(() => {});
    }, []);

    useEffect(() => {
        getAccount().then(response => setAccount(response.data)).catch(() => {});
    }, []);

    const close = () => setOpenModal(null);

    return (
        <div className="page">
            <Navbar />

            <div className="account-container">
                <div className="card account-card">
                    <div className="account-header">
                        <h2>My Account</h2>
                    </div>

                    <div className="account-body">
                        <div className="account-info">
                            <label>Username</label>
                            <span>{account?.username}</span>
                        </div>
                        <div className="account-info">
                            <label>Email</label>
                            <span>{account?.email}</span>
                        </div>
                        <div className="account-info">
                            <label>Role</label>
                            <span>{ROLE_LABELS[account?.role] ?? account?.role}</span>
                        </div>

                        <div className="account-actions">
                            <button className="btn btn-primary" onClick={() => setOpenModal("username")}>Change username</button>
                            <button className="btn btn-primary" onClick={() => setOpenModal("email")}>Change email</button>
                            <button className="btn btn-primary" onClick={() => setOpenModal("password")}>Change password</button>
                        </div>

                        <hr />

                        <div className="danger-zone">
                            <h5 className="text-danger">Danger zone</h5>
                            <button className="btn btn-outline-danger" onClick={() => setOpenModal("delete")}>Delete account</button>
                        </div>
                    </div>
                </div>
            </div>

            {openModal === "username" && <ChangeUsernameModal show onClose={close} onChanged={loadAccount} />}
            {openModal === "email" && <ChangeEmailModal show onClose={close} onChanged={loadAccount} />}
            {openModal === "password" && <ChangePasswordModal show onClose={close} />}
            {openModal === "delete" && <DeleteAccountModal show onClose={close} />}
        </div>
    );
}

export default Account;
