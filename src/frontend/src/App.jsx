import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import RosterMenu from "./pages/RosterMenu";
import Builder from "./pages/Builder";
import Account from "./pages/Account";
import AdminPanel from "./pages/AdminPanel";
import AddUnit from "./pages/AddUnit";
import DeleteUnit from "./pages/DeleteUnit";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/rosters" element={<RosterMenu />} />
                <Route path="/builder/:id" element={<Builder />} />
                <Route path="/account" element={<Account />} />
                <Route path="/admin" element={<AdminPanel />} />
                <Route path="/admin/add-unit" element={<AddUnit />} />
                <Route path="/admin/delete-unit" element={<DeleteUnit />} />
                <Route path="*" element={<Navigate to="/rosters" replace />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
