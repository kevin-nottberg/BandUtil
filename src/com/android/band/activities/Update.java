package com.android.band.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFile.DownloadProgressListener;

public class Update extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<MetadataBufferResult>{
	
	GoogleApiClient apiClient;
	
	protected String mAccountName;
	
	protected static final String EXTRA_ACCOUNT_NAME = "account_name";
	protected static final int REQUEST_CODE_RESOLUTION = 1;
    protected static final int NEXT_AVAILABLE_REQUEST_CODE = 2;
    protected static final int REQUEST_CODE_CREATOR = 3;
    protected static final int REQUEST_CODE_OPENER = 4;
    
	protected boolean readMasterDotFile;

	private DriveId mSelectedFileDriveId;
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		if (savedInstanceState != null) {
            mAccountName = savedInstanceState.getString( EXTRA_ACCOUNT_NAME );
        }
        if (mAccountName == null) {
            mAccountName = getIntent().getStringExtra( EXTRA_ACCOUNT_NAME );
        }

        if (mAccountName == null) {
            Account[] accounts = AccountManager.get(this).getAccountsByType( "com.google" );
            if (accounts.length == 0) {
                Toast.makeText( this, "Must have a Google account installed", Toast.LENGTH_SHORT );
                return;
            }
            mAccountName = accounts[0].name;
        }
        
        readMasterDotFile = false;

		
	}
	
	@Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );
        outState.putString( EXTRA_ACCOUNT_NAME, mAccountName );
    }
	
	protected void onResume() {
		Log.d("Testing", "onResume()");
        super.onResume();

        if (mAccountName == null) {
            return;
        }
        
        Log.d("Testing", "onResume() - 2");
        
        if (apiClient == null) {
            // permissions to query available accounts.
            apiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
        }

        if( readMasterDotFile == true ) {
        	return;
        }
        
        try {
        	InputStream in = openFileInput("bdu_master_dot_fileid");
        	
        	if( in != null ) {
        		InputStreamReader tmp = new InputStreamReader(in);
        		BufferedReader reader = new BufferedReader(tmp);
        		String str;
        		StringBuilder buf = new StringBuilder();
        		
        		while( (str = reader.readLine()) != null ) {
        			buf.append(str+"\n");
        		}
        		
        		in.close();
        	
        		if( buf.length() > 0 ) {
        		    mSelectedFileDriveId = DriveId.decodeFromString( buf.toString() );
        		
        		    Log.i("testing", "Using previously selected file: " + mSelectedFileDriveId.encodeToString() );
        		}
        	}
        }
        catch( java.io.FileNotFoundException e ) {
        	// The file hasn't been created yet.  This is not an error and will just cause the 
        	// file selection dialog to pop up later.
        }
        catch( Throwable t ) {
        	Toast.makeText( this, "Exception: " + t.toString(), Toast.LENGTH_SHORT ).show();
    	    Log.i("testing","fileid-load", t);
        }
        
        apiClient.connect();
    }
	
    /**
     * Called when activity gets invisible. Connection to Drive service needs to
     * be disconnected as soon as an activity is invisible.
     */	
	
	/** 
	 * Writes the drive scope id to the a local file
	 */
    protected void onPause() {
		Log.d("Testing", "onPause()");
        if (apiClient != null) {
            apiClient.disconnect();
        }
        super.onPause();
        
        Log.d("Testing", "onPause(): " + mSelectedFileDriveId );
        
        if( mSelectedFileDriveId != null ) {
            try {
        	    OutputStreamWriter out = new OutputStreamWriter( openFileOutput("bdu_master_dot_fileid", 0 ) );
        	
        	    out.write( mSelectedFileDriveId.encodeToString() + "\n" );
        	    out.close();
        	    
        	    Log.i("testing","fileid-save: " + mSelectedFileDriveId.encodeToString() );
            } catch( Throwable t ) {
        	    Toast.makeText( this, "Exception: " + t.toString(), Toast.LENGTH_SHORT ).show();
        	    Log.i("testing","fileid-save-exception", t);
            }
        }
    }
	
	@Override
	public void onConnected( Bundle connectionHint ) {
		Log.d("testing", "Connected YAYAYAYAYAY!");
        // If there is a selected file, open its contents.
        if( mSelectedFileDriveId != null ) {
            open();
            return;
        }
		
	    IntentSender intentSender = Drive.DriveApi
		            .newOpenFileActivityBuilder()
		            .setMimeType(new String[] { "txt/plain", "text/plain", "text/html", "application/xml" })
		            .build( apiClient );
		try {
		    startIntentSenderForResult( intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0 );
		} catch (SendIntentException e) {
		    Log.w("testing", "Unable to send intent", e);
		}
	}

	@Override
	public void onConnectionSuspended( int cause ) {
		Log.d("testing", "Connection suspended");
	}

	@Override
	public void onConnectionFailed( ConnectionResult result ) {
		Log.d("Testing", "onConnectionFailed()");
		if (!result.hasResolution()) {
			Log.d("Testing", "onConnectionFailed() - no resolution");
			Log.d("Testing", result.toString());
			Log.d("Testing", GooglePlayServicesUtil.getErrorString(result.getErrorCode()));
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (SendIntentException e) {
            Log.d("testing", "Exception while starting resolution activity", e);
        }	
	}
	
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		Log.d("Testing", "onActivityResult()");
		super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RESOLUTION && resultCode == RESULT_OK) {
            apiClient.connect();
        }
        else if( requestCode == REQUEST_CODE_CREATOR ) {
            // Called after a file is saved to Drive.
            if (resultCode == RESULT_OK) {
                Log.i("testing", "File created.");
                //search();
            }
        }
        else if( requestCode == REQUEST_CODE_OPENER ) {
            Log.i("testing", "File opener: " + resultCode);
            // Called after a file is saved to Drive.
            if (resultCode == RESULT_OK) {
                Log.i("testing", "File opener.");
                mSelectedFileDriveId = (DriveId) data.getParcelableExtra( OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID );
                Log.i("testing", "selectedFileID: " + mSelectedFileDriveId.encodeToString());
                
                
                //search();
            }
        }        
	}
	
	private void open() {
        // Reset progress dialog back to zero as we're
        // initiating an opening request.
        //mProgressBar.setProgress(0);
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void onProgress(long bytesDownloaded, long bytesExpected) {
                // Update progress dialog with the latest progress.
                int progress = (int)(bytesDownloaded*100/bytesExpected);
                Log.d("testing", String.format("Loading progress: %d percent", progress));
                //mProgressBar.setProgress(progress);
            }
        };
        Drive.DriveApi.getFile( apiClient, mSelectedFileDriveId )
            .openContents( apiClient, DriveFile.MODE_READ_ONLY, listener)
            .setResultCallback(contentsCallback);
        
        readMasterDotFile = true;
    }
	
	private ResultCallback<ContentsResult> contentsCallback = new ResultCallback<ContentsResult>() {
        @Override
        public void onResult(ContentsResult result) {
            if (!result.getStatus().isSuccess()) {
                Log.i("testing", "Error while opening the file contents");
                return;
            }
            Log.i("testing", "File contents opened");
            
            try {
                InputStream inStream = result.getContents().getInputStream();
            
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
            
                Log.i("testing", "Content: " + out.toString() );
            
            } catch( IOException ioe ) {
                Log.i("testing", "IOException: " + ioe.getMessage() );
            }
        }
    };

	@Override
	public void onResult(MetadataBufferResult result) {
		// TODO Auto-generated method stub
		
	}

}
