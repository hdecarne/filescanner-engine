/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.filescanner.engine.format;

import java.io.IOException;
import java.nio.ByteBuffer;

import de.carne.filescanner.engine.FileScannerResultDecodeContext;
import de.carne.filescanner.engine.FileScannerResultRenderContext;
import de.carne.filescanner.engine.transfer.FileScannerResultOutput;

/**
 *
 */
public class VArraySpec extends CompositeSpec {

	/**
	 *
	 */
	public VArraySpec() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isFixedSize() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int matchSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean matches(ByteBuffer buffer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void decodeComposite(FileScannerResultDecodeContext context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderComposite(FileScannerResultOutput out, FileScannerResultRenderContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub

	}

}
