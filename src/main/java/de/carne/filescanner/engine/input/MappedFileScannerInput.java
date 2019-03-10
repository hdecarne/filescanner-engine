/*
 * Copyright (c) 2007-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.filescanner.engine.input;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.Nullable;

import de.carne.boot.check.Check;

/**
 * {@linkplain FileScannerInput} implementation that provides a combined and mapped view to other input's data sections.
 */
public class MappedFileScannerInput extends FileScannerInput {

	private NavigableMap<Long, Mapping> mappings = new TreeMap<>();

	/**
	 * Constructs a new {@linkplain MappedFileScannerInput} instance.
	 *
	 * @param name the input name.
	 */
	public MappedFileScannerInput(String name) {
		super(name);
	}

	/**
	 * Adds a mapping for the given input data section to this input.
	 *
	 * @param input the input to map.
	 * @param start the start position of the mapping.
	 * @param end the end position of the mapping.
	 * @return the updated {@linkplain MappedFileScannerInput} instance.
	 */
	public MappedFileScannerInput add(FileScannerInput input, long start, long end) {
		Check.assertTrue(start <= end);

		if (start < end) {
			this.mappings.put(size(), new Mapping(input, start, end));
		}
		return this;
	}

	@Override
	public void close() {
		// Nothing to do here
	}

	@Override
	public long size() {
		Map.Entry<Long, Mapping> lastEntry = this.mappings.lastEntry();

		return (lastEntry != null ? lastEntry.getKey().longValue() + lastEntry.getValue().length() : 0);
	}

	@Override
	public int read(ByteBuffer buffer, long position) throws IOException {
		Map.Entry<Long, Mapping> entry = this.mappings.ceilingEntry(position);
		int totalRead = -1;

		while (entry != null && buffer.hasRemaining()) {
			long mappingOffset = position - entry.getKey();
			Mapping mapping = entry.getValue();
			long mappingRemaining = mapping.length() - mappingOffset;

			if (mappingRemaining > 0) {
				int read = mapping.read(buffer, mappingOffset);

				if (read > 0) {
					totalRead = Math.max(0, totalRead) + read;
					entry = nextMapping(entry, read);
				} else {
					entry = null;
				}
			} else {
				entry = null;
			}
		}
		return totalRead;
	}

	private Map.@Nullable Entry<Long, Mapping> nextMapping(Map.Entry<Long, Mapping> current, int step) {
		Map.@Nullable Entry<Long, Mapping> next = this.mappings.ceilingEntry(current.getKey() + step);

		return (next != current ? next : null);
	}

	private class Mapping {

		private final FileScannerInput input;
		private final long start;
		private final long length;

		protected Mapping(FileScannerInput input, long start, long end) {
			this.input = input;
			this.start = start;
			this.length = end - start;
		}

		public long length() {
			return this.length;
		}

		public final int read(ByteBuffer buffer, long offset) throws IOException {
			int limit = (int) Math.min(buffer.remaining(), this.length - offset);
			ByteBuffer limitedBuffer = buffer.duplicate();

			limitedBuffer.limit(limitedBuffer.position() + limit);

			int read = this.input.read(limitedBuffer, this.start + offset);

			buffer.position(limitedBuffer.position());
			return read;
		}

	}

}
