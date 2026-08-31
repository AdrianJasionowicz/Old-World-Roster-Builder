import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";

import ChangeUsernameModal from "../components/ChangeUsernameModal";
import ChangeEmailModal from "../components/ChangeEmailModal";
import ChangePasswordModal from "../components/ChangePasswordModal";
import DeleteAccountModal from "../components/DeleteAccountModal";

import { getAccount } from "../api/accountApi";

import "../styles/account.css";

function Account() {

    const [account, setAccount] = useState(null);

    const [showUsernameModal, setShowUsernameModal] = useState(false);

    const [showEmailModal, setShowEmailModal] = useState(false);

    const [showPasswordModal, setShowPasswordModal] = useState(false);

    const [showDeleteModal, setShowDeleteModal] = useState(false);

    useEffect(() => {

        loadAccount();

    }, []);

    async function loadAccount() {

        try {

            const response = await getAccount();

            setAccount(response.data);

        } catch (e) {

            console.error(e);

        }

    }

    return (

        <>

            <Navbar />

            <div className="account-container">

                <div className="card shadow account-card">

                    <div className="account-header">

                        <h2>

                            👤 My Account

                        </h2>

                    </div>

                    <div className="account-body">

                        <div className="account-info">

                            <label>

                                Username

                            </label>

                            <span>

                                {account?.username}

                            </span>

                        </div>

                        <div className="account-info">

                            <label>

                                Email

                            </label>

                            <span>

                                {account?.email}

                            </span>

                        </div>

                        <div className="account-info">

                            <label>

                                Role

                            </label>

                            <span>

                                {account?.role}

                            </span>

                        </div>

                        <div className="account-actions">

                            <button

                                className="btn btn-primary"

                                onClick={() => setShowUsernameModal(true)}

                            >

                                👤 Change Username

                            </button>

                            <button

                                className="btn btn-primary"

                                onClick={() => setShowEmailModal(true)}

                            >

                                📧 Change Email

                            </button>

                            <button

                                className="btn btn-primary"

                                onClick={() => setShowPasswordModal(true)}

                            >

                                🔒 Change Password

                            </button>

                        </div>

                        <hr />

                        <div className="danger-zone">

                            <h5 className="text-danger">

                                Danger Zone

                            </h5>

                            <button

                                className="btn btn-outline-danger"

                                onClick={() => setShowDeleteModal(true)}

                            >

                                🗑 Delete Account

                            </button>

                        </div>

                    </div>

                </div>

            </div>

            {showUsernameModal && (

                <ChangeUsernameModal

                    show={true}

                    onClose={() => {

                        setShowUsernameModal(false);

                        loadAccount();

                    }}

                />

            )}

            {showEmailModal && (

                <ChangeEmailModal

                    show={true}

                    onClose={() => {

                        setShowEmailModal(false);

                        loadAccount();

                    }}

                />

            )}

            {showPasswordModal && (

                <ChangePasswordModal

                    show={true}

                    onClose={() => {

                        setShowPasswordModal(false);

                    }}

                />

            )}

            {showDeleteModal && (

                <DeleteAccountModal

                    show={true}

                    onClose={() => {

                        setShowDeleteModal(false);

                    }}

                />

            )}

        </>

    );

}

export default Account;