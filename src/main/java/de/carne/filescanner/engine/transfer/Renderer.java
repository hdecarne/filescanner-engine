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
package de.carne.filescanner.engine.transfer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;

/**
 * Render interface responsible for generation of the actual output during rendering.
 */
public interface Renderer extends Closeable {

	/**
	 * Emit any necessary prologue output.
	 *
	 * @param options The rendering options.
	 * @throws IOException if an I/O error occurs.
	 */
	default void emitPrologue(Set<RenderOption> options) throws IOException {
		// Default is to emit nothing
	}

	/**
	 * Emit simple text.
	 *
	 * @param style the {@linkplain RenderStyle} to use.
	 * @param text the text to emit.
	 * @param lineBreak whether to emit a line break after the text ({@code true}) or not ({@code false}).
	 * @return the number of emitted characters (for output limiting).
	 * @throws IOException if an I/O error occurs.
	 */
	int emitText(RenderStyle style, String text, boolean lineBreak) throws IOException;

	/**
	 * Emit simple text.
	 *
	 * @param style the {@linkplain RenderStyle} to use.
	 * @param text the text to emit.
	 * @param href the position to link to.
	 * @param lineBreak whether to emit a line break after the text ({@code true}) or not ({@code false}).
	 * @return the number of emitted characters (for output limiting).
	 * @throws IOException if an I/O error occurs.
	 */
	default int emitText(RenderStyle style, String text, long href, boolean lineBreak) throws IOException {
		// Default is to emit without a link
		return emitText(style, text, lineBreak);
	}

	/**
	 * Emit any necessary epilogue output.
	 *
	 * @throws IOException if an I/O error occurs.
	 */
	default void emitEpilouge() throws IOException {
		// Default is to emit nothing
	}

}
