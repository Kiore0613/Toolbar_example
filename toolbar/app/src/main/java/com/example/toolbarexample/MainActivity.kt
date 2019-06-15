package com.example.toolbarexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.ShareActionProvider
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val searchHint = "Enter a word"
    private val tagListener = "LISTENERFOCUS"
    private val tagChange = "OnQueryTextChange"
    private val tagSubmit = "OnQueryTextSubmit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        btn_next.setOnClickListener { nextActivity() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        //toolbar search
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as SearchView

        searchView.queryHint = searchHint
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            Log.d(tagListener, hasFocus.toString())
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(tagSubmit, query)
                return true
            }

            override fun onQueryTextChange(nextText: String?): Boolean {
                Log.d(tagChange, nextText)
                return true
            }
        })

        //toolbar share
        val share = menu.findItem(R.id.menu_share)
        val shareActionProvider = MenuItemCompat.getActionProvider(share) as ShareActionProvider

        intentShare(shareActionProvider)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_fav -> {
                Toast.makeText(this, "element added to favorites", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun nextActivity() {
        val intent = Intent(this, Screen2Activity::class.java)
        startActivity(intent)
    }

    private fun intentShare(shareActionProvider: ShareActionProvider) {
        if (shareActionProvider != null) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "This is a Shared message")
            shareActionProvider.setShareIntent(intent)
        }
    }
}
