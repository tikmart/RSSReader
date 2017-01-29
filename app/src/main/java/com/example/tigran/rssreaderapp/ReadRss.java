package com.example.tigran.rssreaderapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ReadRss extends AsyncTask {
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    Context context;
    ProgressDialog progressDialog;
    String webAdress = "https://naked-science.ru/sci_rss.xml";

    //to create ProgressDialogue which will show the msg to user
    public ReadRss(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading data...");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        ProcessXML(getData());
        return null;
    }

    private void ProcessXML(Document data) {
        //created a root element which will store the root element
        //created a NodeList which will store child of channel element
        if (getData() != null) {
            feedItems = new ArrayList<>();    //to store every item in single item
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);
                //to check that our node is item node

                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();                      //for every item create a new FeedItem
                    NodeList itemChilds = currentChild.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node current = itemChilds.item(j);
                        //checking if node item is title(description etc) item
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                            //this will return thumbnail URL
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setThumbnailURL(url);

                        }


                    }
                    feedItems.add(item);      //adding the items to ArrayList
                }

            }
        }
    }

    //parsing inputStream with Document Builder so it will return xml document
    public Document getData() {
        try {
            URL url = new URL(webAdress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //show the msg to user that data is loading
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    //dismiss the progressDialog
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        MyAdapter adapter = new MyAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new VerticalSpace(50));

    }
}
