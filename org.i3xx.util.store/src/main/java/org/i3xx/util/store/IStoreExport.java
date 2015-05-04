package org.i3xx.util.store;

/*
 * #%L
 * NordApp OfficeBase :: util :: store
 * %%
 * Copyright (C) 2013 - 2015 I.D.S. DialogSysteme GmbH
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
import java.math.BigInteger;

public interface IStoreExport {

	/**
	 * Adds a suffix to each store file that is not intern.
	 * 
	 * @param key
	 * @param intern
	 * @param suffix
	 * @throws IOException 
	 */
	void export(BigInteger key, BigInteger[] intern, String suffix) throws IOException;
	
	/**
	 * Return the exported files
	 * 
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	File[] getExported(BigInteger key) throws IOException;
	
	/**
	 * Removes the suffix from each exported store file.
	 * This is also used if an extern program create files like a converter
	 *  
	 * @param suffix The suffix without '.'
	 * @throws IOException
	 */
	void reimport(BigInteger key, String suffix) throws IOException;
}
