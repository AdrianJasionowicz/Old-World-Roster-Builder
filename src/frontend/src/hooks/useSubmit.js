import { useState } from "react";

import { errorMessage } from "../api/api";

export function useSubmit() {
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    async function submit(action) {
        setSaving(true);
        setError("");
        try {
            await action();
            return true;
        } catch (e) {
            setError(errorMessage(e));
            return false;
        } finally {
            setSaving(false);
        }
    }

    return { submit, saving, error, setError };
}
