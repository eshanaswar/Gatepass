package vvv.gatepass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem mPreviousMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        String acc_type;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                acc_type = "ERROR";
            }
            else {
                acc_type = extras.getString("ACC_TYPE");
            }
            UserProfile firstFragment = new UserProfile();
//          firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFragment, firstFragment).commit();
        }
        else {
            acc_type = getIntent().getExtras().getString("ACC_TYPE");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        expandNavigationView(acc_type);

        View nav_header = navigationView.inflateHeaderView(R.layout.nav_header_user_page);
        TextView name = (TextView) nav_header.findViewById(R.id.textViewName);
        name.setText("Name");
        TextView email = (TextView) nav_header.findViewById(R.id.textViewEmail);
        email.setText("Email");
    }

    private void expandNavigationView(String acc_type) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.clear();

        String [] menu_list = {"If you see this", "report bug"};
        if (acc_type.equals("STUDENT")) {
            menu_list = new String[] {  "User Profile", "Out Station Request", "Non returnable Gatepass",
                                        "Check Gatepass Status", "Visitor's Gatepass", "Visitor's Gatepass Status"};

            menu.add(R.id.group_menu, Menu.NONE, Menu.NONE, menu_list[0]).setIcon(R.drawable.ic_menu_camera);
            final SubMenu subMenu = menu.addSubMenu("Gatepass");
            for(int i = 1; i<menu_list.length;i++){
                subMenu.add(R.id.group_menu, Menu.NONE, Menu.NONE, menu_list[i]).setIcon(R.drawable.ic_menu_camera);
            }
        }
        else if (acc_type.equals("WARDEN")) {
            menu_list = new String[] {  "Respond to request", "View User", "Visitor request", "Defaulter's list", "Blacklist Students",
                    "Generate Report", "Checkout report"};
            for ( String aMenu_list: menu_list) {
                menu.add(R.id.group_menu, Menu.NONE, Menu.NONE, aMenu_list).setIcon(R.drawable.ic_menu_camera);
            }
        }

        /*
        for ( String aMenu_list: menu_list) {
            menu.add(R.id.group_menu, Menu.NONE, Menu.NONE, aMenu_list).setIcon(R.drawable.ic_menu_camera);
        }*/
        menu.setGroupCheckable(R.id.group_menu, true, true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            UserPage.super.onBackPressed();
                        }
                    }).create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (mPreviousMenuItem != null) {
                mPreviousMenuItem.setChecked(false);
        }
        mPreviousMenuItem = item;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass;
            fragmentClass = UserProfile.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        item.setChecked(true);
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public static class UserProfile extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_user_profile,
                    container, false);
        }
    }
}
