package uk.ac.le.cs.C03102;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.net.*;

interface RESTInterface extends Remote {
    public boolean authenticateUser(String account, String password, URL url)
            throws RemoteException, MalformedURLException;

    void deleteUser(String user);
}
