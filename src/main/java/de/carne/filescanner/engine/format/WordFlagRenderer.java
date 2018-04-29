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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Word attribute {@linkplain FlagRenderer}.
 */
public class WordFlagRenderer extends FlagRenderer<Short> {

	// Serialization support
	private static final long serialVersionUID = -1868333347932279815L;

	private static final short MSB = (short) 0b1000000000000000;

	@Override
	protected Iterator<Short> flags() {
		return new Iterator<Short>() {

			private short nextFlag = MSB;

			@Override
			public boolean hasNext() {
				return this.nextFlag != 0;
			}

			@Override
			public Short next() {
				if (this.nextFlag == 0) {
					throw new NoSuchElementException();
				}

				short flag = this.nextFlag;

				this.nextFlag = shift(this.nextFlag);
				return flag;
			}

		};
	}

	@Override
	protected boolean testFlag(Short value, Short flag) {
		short flagValue = flag.shortValue();

		return (value.shortValue() & flagValue) == flagValue;
	}

	@Override
	protected String formatFlag(Short flag) {
		StringBuilder buffer = new StringBuilder();
		short flagValue = flag.shortValue();

		for (short mask = MSB; mask != 0; mask = shift(mask)) {
			buffer.append((flagValue & mask) == mask ? '1' : '.');
		}
		return buffer.toString();
	}

	static short shift(short flag) {
		return (short) ((flag & 0xffff) >>> 1);
	}

}
