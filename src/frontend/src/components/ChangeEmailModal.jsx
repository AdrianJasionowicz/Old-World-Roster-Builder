import { useState } from "react";

import Modal from "./Modal";
import { changeEmail } from "../api/accountApi";
import { useSubmit } from "../hooks/useSubmit";

function ChangeEmailModal({ show, onClose, onChanged }) {
    const [newEmail, setNewEmail] = useState("");
    const [password, setPassword] = useState("");
    const { submit, saving, error } = useSubmit();

    async function handleSave() {
        if (await submit(() => changeEmail({ newEmail, password }))) {
            onChanged?.();
            onClose();
        }
    }

    return (
        <Modal show={show} title="Change Email" onClose={onClose} onSave={handleSave} saving={saving} error={error}>
            <div className="mb-3">
                <label className="form-label">New email</label>
                <input type="email" className="form-control" value={newEmail} onChange={e => setNewEmail(e.target.value)} required autoFocus />
            </div>
            <div>
                <label className="form-label">Current password</label>
                <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required />
            </div>
        </Modal>
    );
}

export default ChangeEmailModal;
