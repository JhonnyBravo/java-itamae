package java_itamae.domain.repository.group;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * {@link GroupRepository} のテスト。
 * @see DirectoryGroup
 * @see FileGroup
 */
@RunWith(Suite.class)
@SuiteClasses({DirectoryGroup.class, FileGroup.class})
public class TestGroupRepository {
}
