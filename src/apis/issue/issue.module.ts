import {Module} from "@nestjs/common";
import {IssueResolver} from "@/apis/issue/issue.resolver";
import {IssueService} from "@/apis/issue/issue.service";
import {PrismaService} from "@/apis/integration/prisma.service";
import {UserService} from "@/apis/user/user.service";

@Module({
  providers:[IssueResolver, PrismaService, IssueService, UserService]
})
export class IssueModule {}