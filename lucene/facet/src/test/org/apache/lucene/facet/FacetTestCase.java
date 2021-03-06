package org.apache.lucene.facet;

import java.util.Random;

import org.apache.lucene.facet.index.params.CategoryListParams;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.encoding.DGapIntEncoder;
import org.apache.lucene.util.encoding.DGapVInt8IntEncoder;
import org.apache.lucene.util.encoding.EightFlagsIntEncoder;
import org.apache.lucene.util.encoding.FourFlagsIntEncoder;
import org.apache.lucene.util.encoding.IntEncoder;
import org.apache.lucene.util.encoding.NOnesIntEncoder;
import org.apache.lucene.util.encoding.SortingIntEncoder;
import org.apache.lucene.util.encoding.UniqueValuesIntEncoder;
import org.apache.lucene.util.encoding.VInt8IntEncoder;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class FacetTestCase extends LuceneTestCase {
  
  private static final IntEncoder[] ENCODERS = new IntEncoder[] {
    new SortingIntEncoder(new UniqueValuesIntEncoder(new VInt8IntEncoder())),
    new SortingIntEncoder(new UniqueValuesIntEncoder(new DGapIntEncoder(new VInt8IntEncoder()))),
    new SortingIntEncoder(new UniqueValuesIntEncoder(new DGapVInt8IntEncoder())),
    new SortingIntEncoder(new UniqueValuesIntEncoder(new DGapIntEncoder(new EightFlagsIntEncoder()))),
    new SortingIntEncoder(new UniqueValuesIntEncoder(new DGapIntEncoder(new FourFlagsIntEncoder()))),
    new SortingIntEncoder(new UniqueValuesIntEncoder(new DGapIntEncoder(new NOnesIntEncoder(3)))),
    new SortingIntEncoder(new UniqueValuesIntEncoder(new DGapIntEncoder(new NOnesIntEncoder(4)))), 
  };
  
  /** Returns a {@link CategoryListParams} with random {@link IntEncoder} and field. */
  public static CategoryListParams randomCategoryListParams() {
    final String field = CategoryListParams.DEFAULT_FIELD + "$" + random().nextInt();
    return randomCategoryListParams(field);
  }
  
  /** Returns a {@link CategoryListParams} with random {@link IntEncoder}. */
  public static CategoryListParams randomCategoryListParams(String field) {
    Random random = random();
    final IntEncoder encoder = ENCODERS[random.nextInt(ENCODERS.length)];
    return new CategoryListParams(field) {
      @Override
      public IntEncoder createEncoder() {
        return encoder;
      }
    };
  }
  
}
