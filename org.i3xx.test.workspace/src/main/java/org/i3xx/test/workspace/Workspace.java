package org.i3xx.test.workspace;

/*
 * #%L
 * workspace location
 * %%
 * Copyright (C) 2014 - 2015 I.D.S. DialogSysteme GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * <p>This class is made to use the maven tests and the junit tests in the eclipse sdk.</p>
 * 
 * <p><b>JUnit:</b></p>
 * <p>The junit tests need a jvm property set ('org.i3xx.step.uno' is the project name).</p>
 * 
 * <tt>-Dworkspace.home=${workspace_loc:org.i3xx.step.uno}</tt>
 * 
 * <p><b>Maven2:</b></p>
 * <p>The maven2 unit tests need a property set in the pom file.</p>
 * 
 * <pre>
 *   &lt;plugin&gt;
 *     &lt;artifactId&gt;maven-surefire-plugin&lt;/artifactId&gt;
 *     &lt;configuration&gt;
 *       &lt;systemPropertyVariables&gt;
 *         &lt;workspace.home&gt;${basedir}&lt;/workspace.home&gt;
 *       &lt;/systemPropertyVariables&gt;
 *     &lt;/configuration&gt;
 *   &lt;/plugin&gt;
 * </pre>
 * 
 * @author Stefan
 *
 */
public class Workspace {

	public static String location() throws IOException {
		
		//
		//Read the system property 'workspace.home'
		//
		String prop = System.getProperty("workspace.home");
		if(prop != null)
			return prop;
		
		//
		// Get resource file
		//
		String resource = "org/i3xx/test/workspace/TestHome.cfg";
		
		URL url = Workspace.class.getResource("TestHome.cfg");
		if(url==null)
			return null;
		
		//
		//Read location from file content
		//
		
		Reader in = new InputStreamReader( Workspace.class.getResourceAsStream("TestHome.cfg") );
		StringBuffer out = new StringBuffer();
		try{
			int c = 0;
			char[] cbuf = new char[16];
			while((c=in.read(cbuf))>-1)
				out.append(cbuf, 0, c);
		}finally{
			in.close();
		}
		int i = out.indexOf("=");
		if(i>-1){
			String path = out.substring(i+1).trim();
			if(path.length()>0)
				return path;
		}
		
		//
		//Read location from file system path (supports no jar - because it points to the Maven repository)
		//
		
		//Note a jar starts with 'file:/' and ends with '.jar!/'
		
		String srcPath = url.getFile();
		if(srcPath.length()<=1)
			return null;
		
		//skip leading '/' if os is windows
		if(File.separatorChar=='\\') {
			srcPath = srcPath.substring(1);
		}
		srcPath = nibble(srcPath, resource);
		srcPath = nibble(srcPath, "/target/classes/");
		
		return srcPath.replace('/', File.separatorChar);
	}
	
	/**
	 * Remove the tail from the statement
	 * 
	 * @param stmt The statement
	 * @param tail The tail to remove
	 * @return The statement without the tail
	 */
	public static String nibble(String stmt, String tail) {
		if( stmt.indexOf(tail)<0)
			throw new IllegalArgumentException("Parameter missmatch '"+stmt+
					"' doesn't contains '"+tail+"'.");
		
		return stmt.substring(0, stmt.length()-tail.length());
	}
	
}
