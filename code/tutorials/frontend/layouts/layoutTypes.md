# CSS Layout Types

When designing a web application, understanding different CSS layout types is crucial for positioning and organizing
content effectively. Below is an overview of the main CSS layout types&techniques:

- Normal flow: Default positioning.
- Display(block, flex ..)
- Flexbox (Flexible Box Layout) One-dimensional layout for alignment and distribution.
- Grid Layout: Two-dimensional layout for complex designs.
- Positioning: Fine-tuned control over exact element positioning
- Float: Used for wrapping text around elements
- Table
- Multi-column layout

## 1. Normal Flow

- **Description**: The default layout mode where elements are positioned according to their order in the HTML document.
  Block elements stack vertically, while inline elements flow horizontally.
- **Use Case**: Basic document structure without advanced positioning.

## 2. Flexbox (Flexible Box Layout)

- **Description**: A one-dimensional layout model that allows you to align and distribute space among items in a
  container. It handles layout in a single direction (row or column).
- **Use Case**: Aligning items within a container, distributing space dynamically, responsive designs.

### Properties Applied to Flex Container

These properties determine how the flex items are laid out within the container.

1. **`display: flex;`**

- **Description**: Turns an element into a flex container, enabling flexbox layout for its children.
- **Effect**: Once you apply `display: flex;` to a container, all its direct children become flex items and are laid out
  according to the flexbox model.

2. **`flex-direction`**

- **Description**: Determines the direction of the flex items within the container.
- **Possible Values**:
    - `row`: Flex items are laid out in a row, from left to right. (default)
    - `row-reverse`: Flex items are laid out in a row, but in reverse order (right to left).
    - `column`: Flex items are laid out in a column, from top to bottom.
    - `column-reverse`: Flex items are laid out in a column, but in reverse order (bottom to top).

3. **`flex-wrap`**

- **Description**: Controls whether flex items should wrap onto multiple lines if there isn't enough space in one line.
- **Possible Values**:
    - `nowrap`: All flex items will be laid out in a single line, even if it overflows the container. (default)
    - `wrap`: Flex items will wrap onto multiple lines from top to bottom (for `row` direction) or left to right (
      for `column` direction).
    - `wrap-reverse`: Flex items will wrap onto multiple lines in reverse order, from bottom to top (for `row`
      direction) or right to left (for `column` direction).

4. **`flex-flow`**

- **Description**: A shorthand for setting both `flex-direction` and `flex-wrap` in a single declaration.
- **Example**: `flex-flow: row wrap;`
    - This means the flex items will be laid out in a row, and if necessary, they will wrap onto multiple lines.

5. **`justify-content`**

- **Description**: Aligns flex items along the main axis (horizontally for `row`, vertically for `column`).
- **Possible Values**:
    - `flex-start`: Flex items are aligned to the start of the container. (default)
    - `flex-end`: Flex items are aligned to the end of the container.
    - `center`: Flex items are centered along the main axis.
    - `space-between`: Flex items are evenly distributed, with the first item at the start and the last item at the end.
    - `space-around`: Flex items are evenly distributed with equal space around them.
    - `space-evenly`: Flex items are evenly distributed with equal space between them, including the start and end.

6. **`align-items`**

- **Description**: Aligns flex items along the cross axis (perpendicular to the main axis).
- **Possible Values**:
    - `stretch`: Flex items stretch to fill the container along the cross axis. (default)
    - `flex-start`: Flex items are aligned to the start of the cross axis.
    - `flex-end`: Flex items are aligned to the end of the cross axis.
    - `center`: Flex items are centered along the cross axis.
    - `baseline`: Flex items are aligned such that their baselines align.

7. **`align-content`**

- **Description**: Aligns flex lines (when there are multiple lines) along the cross axis.
- **Possible Values**:
    - `stretch`: Flex lines stretch to fill the container along the cross axis. (default)
    - `flex-start`: Flex lines are aligned to the start of the cross axis.
    - `flex-end`: Flex lines are aligned to the end of the cross axis.
    - `center`: Flex lines are centered along the cross axis.
    - `space-between`: Flex lines are evenly distributed with no space at the start and end.
    - `space-around`: Flex lines are evenly distributed with equal space around each line.

### Properties Applied to Flex Items

These properties control how individual flex items behave within the flex container.

1. **`order`**

- **Description**: Defines the order in which a flex item appears within the flex container.
- **Default Value**: `0`.
- **Effect**: Items with a lower `order` value will appear before items with a higher `order` value.

2. **`flex-grow`**

- **Description**: Defines how much a flex item will grow relative to the other flex items.
- **Default Value**: `0` (flex items will not grow).
- **Effect**: A flex item with a `flex-grow` value of `1` will take up any available space in the container, relative to
  other flex items' `flex-grow` values.

3. **`flex-shrink`**

- **Description**: Defines how much a flex item will shrink relative to the other flex items when there isn’t enough
  space.
- **Default Value**: `1` (flex items can shrink if needed).
- **Effect**: A flex item with a `flex-shrink` value of `1` will shrink proportionally with other flex items to fit
  within the container.

4. **`flex-basis`**

- **Description**: Specifies the initial size of the flex item before any available space is distributed.
- **Default Value**: `auto`.
- **Effect**: The flex item will be the size defined by `flex-basis` before space is distributed based on `flex-grow`
  or `flex-shrink`.

5. **`flex`**

- **Description**: A shorthand for setting `flex-grow`, `flex-shrink`, and `flex-basis` in a single declaration.
- **Example**: `flex: 1 0 auto;`
    - This means the flex item can grow (`1`), will not shrink (`0`), and has an initial size defined by `auto`.

6. **`align-self`**

- **Description**: Overrides the `align-items` property for a specific flex item, allowing individual alignment along
  the cross axis.
- **Possible Values**:
    - `auto`: The flex item inherits the `align-items` value from its parent. (default)
    - `flex-start`: Aligns the flex item to the start of the cross axis.
    - `flex-end`: Aligns the flex item to the end of the cross axis.
    - `center`: Centers the flex item along the cross axis.
    - `baseline`: Aligns the flex item such that its baseline aligns with the others.
    - `stretch`: Stretches the flex item to fill the container along the cross axis.

### Summary

- **Flex Container Properties**: Define how the children (flex items) are distributed and aligned within the container.
- **Flex Item Properties**: Control the size, order, and alignment of individual items within the container.

Using these properties, you can create complex layouts with ease and precision, adjusting the behavior of both the
container and its items.


## 3. Grid Layout

## 4. 
