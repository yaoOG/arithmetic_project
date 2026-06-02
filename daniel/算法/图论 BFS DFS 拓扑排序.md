# 图论 BFS DFS 拓扑排序

## 一、先说结论

图题常见三类：

```text
连通性：DFS / BFS
最短层数：BFS
依赖顺序：拓扑排序
```

写图题先明确：

- 图是有向还是无向。
- 是否有权。
- 节点编号范围。
- 是否可能有环。
- 是否需要 visited。

## 二、图的存储

### 1. 邻接表

适合稀疏图。

```java
List<Integer>[] graph = new ArrayList[n];
for (int i = 0; i < n; i++) {
    graph[i] = new ArrayList<>();
}

graph[u].add(v);
```

### 2. 邻接矩阵

适合稠密图或节点数小。

```java
boolean[][] graph = new boolean[n][n];
graph[u][v] = true;
```

## 三、DFS

适合：

- 连通块数量。
- 岛屿问题。
- 路径搜索。
- 环检测。

模板：

```java
void dfs(int cur, List<Integer>[] graph, boolean[] visited) {
    if (visited[cur]) {
        return;
    }
    visited[cur] = true;

    for (int next : graph[cur]) {
        dfs(next, graph, visited);
    }
}
```

注意：

```text
递归深度过深可能栈溢出，必要时改迭代。
```

## 四、BFS

适合：

- 最短步数。
- 层序扩散。
- 多源扩散。

模板：

```java
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(start);
visited[start] = true;
int step = 0;

while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        int cur = queue.poll();
        for (int next : graph[cur]) {
            if (!visited[next]) {
                visited[next] = true;
                queue.offer(next);
            }
        }
    }
    step++;
}
```

## 五、多源 BFS

适合：

- 腐烂橘子。
- 地图最近距离。
- 多个起点同时扩散。

核心：

```text
一开始把所有起点都放入队列。
```

## 六、拓扑排序

适合：

- 课程表。
- 任务依赖。
- 判断有向图是否有环。

Kahn 算法：

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<Integer>[] graph = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) {
        graph[i] = new ArrayList<>();
    }

    int[] indegree = new int[numCourses];
    for (int[] p : prerequisites) {
        int course = p[0], pre = p[1];
        graph[pre].add(course);
        indegree[course]++;
    }

    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
        }
    }

    int count = 0;
    while (!queue.isEmpty()) {
        int cur = queue.poll();
        count++;
        for (int next : graph[cur]) {
            if (--indegree[next] == 0) {
                queue.offer(next);
            }
        }
    }

    return count == numCourses;
}
```

判断：

```text
如果最终处理节点数小于总节点数，说明有环。
```

## 七、岛屿问题模板

```java
int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

void dfs(char[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
        return;
    }
    if (grid[i][j] != '1') {
        return;
    }

    grid[i][j] = '0';
    for (int[] d : dirs) {
        dfs(grid, i + d[0], j + d[1]);
    }
}
```

## 八、常见错误

- 无向图忘记双向建边。
- visited 标记太晚导致重复入队。
- BFS 层数 step 多加或少加。
- 拓扑排序边方向建反。
- DFS 递归没有终止条件。
- 修改原 grid 后忘记题目是否允许。

## 九、面试表达模板

```text
这题本质是图的连通性/层序扩散/依赖排序问题。
如果是最短步数，我优先 BFS；如果是遍历所有连通区域，可以 DFS；如果是课程依赖这种有向无环判断，就用入度表做拓扑排序。
```

