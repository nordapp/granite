package org.i3xx.util.symbol.service.model;

/*
 * #%L
 * NordApp OfficeBase :: uno
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

public interface SymbolFileLocationService {

	/**
	 * The result of the file location service is the application root directory.
	 * This may be the same as the mandator root if there is one mandator
	 * only and the root is not the default one (absolute root). Usually the
	 * mandator root is the file location added by the mandator's directory.
	 * 
	 * @return The file location (directory)
	 */
	File getLocation() throws IOException;
}
