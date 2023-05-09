import React from 'react'
import { Outlet ,Navigate} from 'react-router-dom'
import { isLoggedIn } from '../auth'

export default function PrivateRoute() {

    return isLoggedIn() ? <Outlet/> : <Navigate to={"/login"}/>
}
