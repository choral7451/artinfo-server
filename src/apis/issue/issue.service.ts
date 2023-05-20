import {Injectable} from "@nestjs/common";
import {PrismaService} from "@/apis/integration/prisma.service";
import {CreateIssueFields} from "@/apis/issue/type/create-issue.fields";
import {Issue, User} from "@prisma/client";

@Injectable()
export class IssueService {
  constructor(
    private readonly prismaService: PrismaService
  ) {}

  async createIssue(fields:CreateIssueFields):Promise<string> {
    const issue = await this.prismaService.issue.create({
      data: fields
    })

    return issue.id
  }

  async getIssueById(id: string): Promise<Issue & { user: User }> {
    const issue =  await this.prismaService.issue.findFirst({
      where: { id }
    })

    if(!issue) throw new Error('THE_POST_DOES_NOT_EXIST')

    return issue
  }
}