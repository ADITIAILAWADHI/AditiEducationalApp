package com.ADITIAILAWADHI.aditieducationalapp;

import android.content.Context;
import android.database.Cursor;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.ADITIAILAWADHI.aditieducationalapp.helper.DBHelper;
import com.ADITIAILAWADHI.aditieducationalapp.helper.QuestionImageManager;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EduGameInstrumentedTest {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Test
    public void useAppContext() {
        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //assertEquals("com.davide.davidejcueducationalapp", appContext.getPackageName());
        testQuestionImagemanager();
        testDB();
    }

    private void testDB() {
        DBHelper dbHelper = new DBHelper(appContext);
        // Clear DB
        dbHelper.getWritableDatabase().execSQL("delete from " + DBHelper.TABLE_NAME);
        dbHelper.insertPlayer("Tony", 120, "1", 20);
        Cursor cursor = dbHelper.getAllPlayers();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex(DBHelper.USERNAME_COL));
            String score = cursor.getString(cursor.getColumnIndex(DBHelper.SCORE_COL));
            String durationStr = cursor.getString(cursor.getColumnIndex(DBHelper.DURATION_COL));
            String level = cursor.getString(cursor.getColumnIndex(DBHelper.LEVEL_COL));

            assertEquals("Tony", username);
            assertEquals("20", score);
            assertEquals("120", durationStr);
            assertEquals("1", level);
        }
    }

    private void testQuestionImagemanager() {
        String levelStr = "1";
        QuestionImageManager questionImageManager = new QuestionImageManager(levelStr, appContext.getAssets());

        assertEquals("21", questionImageManager.getAnswer(1));

    }
}