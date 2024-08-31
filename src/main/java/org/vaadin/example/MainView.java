package org.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.data.PlayerDataService;
import org.vaadin.example.models.Player;
import org.vaadin.example.views.TabbedView;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    TabbedView tabs;

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *                The message service. Automatically injected Spring managed
     *                bean.
     * @throws Exception
     */
    public MainView(@Autowired PlayerDataService playerService) throws Exception {
        tabs = new TabbedView(playerService);

        tabs.setSizeFull();

        add(tabs);

    }

}
