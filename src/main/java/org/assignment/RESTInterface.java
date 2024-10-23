package org.assignment;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RESTInterface extends Remote{
    public boolean authenticateUser(String account, String password, URL url)
            throws RemoteException, MalformedURLException;

    default void deleteUser(String user) {

    }
}
