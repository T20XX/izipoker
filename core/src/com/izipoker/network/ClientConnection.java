package com.izipoker.network;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

/**
 * Created by Telmo on 03/06/2016.
 */
public class ClientConnection {

    private String hostname;
    private int port;
    private CallHandler callHandler;
    private Client client;
    private ServerInterface proxyTable;
    private ServiceListener serviceListener;
    private JmDNS mdnsService;


    /**
     * Performs the connection to the server's IP address, given hostname and port.
     */
    public void connectToServer(String hostname, int port) {
        if (hostname != null) {
            // Retrieves the server's event handler.
            callHandler = new CallHandler();
            try {
                client = new Client(hostname, port, callHandler);
                proxyTable = (ServerInterface) client.getGlobal(ServerInterface.class);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * Performs the connection to the server's IP address.
     */
    public void connectToServer() {
        if (hostname != null) {
            // Retrieves the server's event handler.
            callHandler = new CallHandler();
            try {
                client = new Client(hostname, port, callHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }

            proxyTable = (ServerInterface) client.getGlobal(ServerInterface.class);

            // Creates client handler.
            /*clientHandler = new ClientHandler(this);

            try {
                callHandler.exportObject(AbstractClientHandler.class, clientHandler);
            } catch (LipeRMIException e1) {
                e1.printStackTrace();
                Gdx.app.exit();
            }

            // Attempts to be assigned for a game.
            try {
                if (serverEventHandler != null)
                    firstPlayer = serverEventHandler.getAssigned(clientHandler);
                else Gdx.app.exit();
            } catch (RuntimeException e) {
                Gdx.app.exit();
            }
        }
        else Gdx.app.exit();*/

            //implementar noutra função!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        /*Client client = new Client(remoteHost, portWasBinded, callHandler);

        // create and expose remote listener
        PokerClient listener = new PokerClient();
        callHandler.exportObject(ClientCallbackInterface.class, listener);

        // now do conversation
        if(proxyTable.isLobbyState()) {
            if (!proxyTable.join(nameTF.getText(), listener)) {
                System.out.println("Sorry, nickname is already in use.");
                return;
            } else {
                IZIPokerAndroid.getInstance().setScreen(new GameAndroid(nameTF.getText(), proxyTable, listener));
            }
        }else {
            Dialog resultDialog = new Dialog("Error", skin);
            resultDialog.text("Game has already started");
            resultDialog.button("BACK");
            resultDialog.show(stage);
        }
    } catch (Exception e) {
        System.err.println("Client exception: " + e.toString());
        e.printStackTrace();
    }*/
        }
    }

    /**
     * Finds an mDNS service which corresponds to the server's IP address.
     */
    @SuppressWarnings("deprecation")
    public void findService() {
        // Defines service listener.
        serviceListener = new ServiceListener() {

            @Override
            public void serviceAdded(ServiceEvent arg0) {
                mdnsService.requestServiceInfo("_poker._tcp.local.", arg0.getName());
            }

            @Override
            public void serviceRemoved(ServiceEvent arg0) {
                // Leave empty.
            }

            @Override
            public void serviceResolved(ServiceEvent arg0) {
                hostname = NetworkUtils.parseHostName(arg0.getInfo().getURL());
                port = arg0.getInfo().getPort();

                System.out.println(port);
            }

        };

        InetAddress address = null;
        try {
            address = NetworkUtils.getNetworkAddress();
            System.out.println(address.toString());
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        if (address != null) {
            try {
                mdnsService = JmDNS.create(address);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            mdnsService.addServiceListener("_poker._tcp.local.", serviceListener);
            ServiceInfo[] services = mdnsService.list("_poker._tcp.local.");
            if (hostname == null) {
                for (ServiceInfo service : services) {
                    hostname = NetworkUtils.parseHostName(service.getURL());
                    port = service.getPort();
                    break;
                }
            }
            mdnsService.removeServiceListener("_poker._tcp.local.", serviceListener);
            try {
                mdnsService.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else Gdx.app.exit();
        System.out.println(hostname);
        System.out.println(port);

    }

    public CallHandler getCallHandler() {
        return callHandler;
    }

    public ServerInterface getProxyTable() {
        return proxyTable;
    }
}
