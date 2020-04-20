/*
 * Copyright (c) 2007-2020 Holger de Carne and contributors, All Rights Reserved.
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
import java.util.function.Supplier;

import de.carne.filescanner.engine.FileScannerResultDecodeContext;
import de.carne.filescanner.engine.FileScannerResultRenderContext;
import de.carne.filescanner.engine.transfer.RenderOutput;
import de.carne.filescanner.engine.util.FinalSupplier;

/**
 * {@linkplain FormatSpec} defining a generic byte range to be skipped during decoding.
 * <p>
 * The range size has to be static or has to be defined via a bound attribute of type {@linkplain Number}.
 */
public class SkipSpec implements FormatSpec {

	private Supplier<? extends Number> size = FinalSupplier.of(Integer.valueOf(0));

	/**
	 * Sets the size (in bytes) of this byte range.
	 *
	 * @param sizeSupplier the size (in bytes) of this byte range.
	 * @return the updated {@linkplain SkipSpec} instance for chaining.
	 */
	public SkipSpec size(Supplier<? extends Number> sizeSupplier) {
		this.size = sizeSupplier;
		return this;
	}

	/**
	 * Sets the size (in bytes) of this byte range.
	 *
	 * @param sizeValue the size (in bytes) of this byte range.
	 * @return the updated {@linkplain SkipSpec} instance for chaining.
	 */
	public SkipSpec size(int sizeValue) {
		this.size = FinalSupplier.of(sizeValue);
		return this;
	}

	@Override
	public boolean isFixedSize() {
		return FormatSpecs.isFixedSize(this.size);
	}

	@Override
	public int matchSize() {
		return FormatSpecs.matchSize(this.size);
	}

	@Override
	public boolean matches(ByteBuffer buffer) {
		return FormatSpecs.matches(buffer, this.size);
	}

	@Override
	public void decode(FileScannerResultDecodeContext context) throws IOException {
		context.skip(this.size.get().longValue());
	}

	@Override
	public void render(RenderOutput out, FileScannerResultRenderContext context) throws IOException {
		context.skip(this.size.get().longValue());
	}

}