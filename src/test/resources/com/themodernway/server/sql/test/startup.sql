/*
 * Copyright (c) 2018, The Modern Way. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

CREATE TABLE employees (
  ID NUMERIC UNSIGNED NOT NULL AUTO_INCREMENT,
  NAME VARCHAR (256) NOT NULL,
  PRIMARY KEY (ID)
);
INSERT INTO employees (NAME) VALUES ('Dean');
INSERT INTO employees (NAME) VALUES ('John');
INSERT INTO employees (NAME) VALUES ('Mike');
INSERT INTO employees (NAME) VALUES ('Nick');
