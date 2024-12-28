package ru.nsu.goncharov;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;


public class Main {

    private final static int BUFFER_CAP = 1024;

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Provide more params");
            return;
        }

        int listenPort = Integer.parseInt(args[0]);
        String translationIP = args[1];
        int translationPort = Integer.parseInt(args[2]);

        InetSocketAddress translationSocket = new InetSocketAddress(translationIP, translationPort);

        AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open()
                .bind(new InetSocketAddress("127.0.0.1", listenPort));


        serverSocket.accept(serverSocket, new ClientConnectionHandler(translationSocket));

        while (true) {
        }
    }

    private static class ClientConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

        private final InetSocketAddress translationSocketAddr;

        public ClientConnectionHandler(InetSocketAddress translationSocketAddr) {
            this.translationSocketAddr = translationSocketAddr;
        }

        @Override
        public void completed(AsynchronousSocketChannel clientServerChannel, AsynchronousServerSocketChannel serverSocketChannel) {
            AsynchronousSocketChannel translationChannel;
            try {
                translationChannel = AsynchronousSocketChannel.open();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            translationChannel.connect(translationSocketAddr, clientServerChannel, new TranslationConnectionHandler(translationChannel));

            serverSocketChannel.accept(serverSocketChannel, new ClientConnectionHandler(translationSocketAddr));
        }

        @Override
        public void failed(Throwable exc, AsynchronousServerSocketChannel buffer) {
            System.out.println("Failed to accept connection: " + exc);
        }
    }

    private static class TranslationConnectionHandler implements CompletionHandler<Void, AsynchronousSocketChannel> {
        private final AsynchronousSocketChannel serverTranslationChannel;

        public TranslationConnectionHandler(AsynchronousSocketChannel serverConn) {
            this.serverTranslationChannel = serverConn;
        }

        @Override
        public void completed(Void result, AsynchronousSocketChannel clientServerChannel) {
            ByteBuffer buffer1 = ByteBuffer.allocate(BUFFER_CAP);
            clientServerChannel.read(buffer1, buffer1, new TranslationHandler(clientServerChannel, serverTranslationChannel));

            ByteBuffer buffer2 = ByteBuffer.allocate(BUFFER_CAP);
            serverTranslationChannel.read(buffer2, buffer2, new TranslationHandler(serverTranslationChannel, clientServerChannel));
        }


        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel channel) {
            try {
                channel.close();
            } catch (Exception e) {
                System.out.println("Failed to close client channel " + e);
            }
            System.out.println("Failed to connect to remote. Closing client. " + exc);
        }
    }

    private static class TranslationHandler implements CompletionHandler<Integer, ByteBuffer> {
        private final AsynchronousSocketChannel fromChannel;
        private final AsynchronousSocketChannel toChannel;

        public TranslationHandler(AsynchronousSocketChannel fromChannel, AsynchronousSocketChannel toChannel) {
            this.fromChannel = fromChannel;
            this.toChannel = toChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer buffer) {
            if (buffer.position() == 0) {
                try {
                    fromChannel.close();
                    toChannel.close();
                } catch (Exception e) {
                    System.out.println("Failed to close channel: " + e);
                }
                return;
            }

            buffer.position(0);
            toChannel.write(buffer);
            ByteBuffer newBuffer = ByteBuffer.allocate(BUFFER_CAP);
            fromChannel.read(newBuffer, newBuffer, this);
        }

        @Override
        public void failed(Throwable exc, ByteBuffer buffer) {
            System.out.println("Read operation failed: " + exc);
        }
    }
}